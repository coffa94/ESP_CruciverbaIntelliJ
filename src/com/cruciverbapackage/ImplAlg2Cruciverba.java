package com.cruciverbapackage;

//Coffaro_Davide_mat556603_Progetto ESP cruciverba

import javax.swing.*;
import java.util.ArrayList;

public class ImplAlg2Cruciverba extends ImplementazioneCruciverba {

    //costruttore cruciverba con una struttura passata in input che richiama il costruttore del parole
    public ImplAlg2Cruciverba(JPanel panel, char matrice[][], String parolaIniziale, int posizioneRigaIniziale, int posizioneColonnaIniziale, ArrayList<String> dizionarioInput, char orientamento) {
        super(panel,matrice,parolaIniziale,posizioneRigaIniziale,posizioneColonnaIniziale,dizionarioInput, orientamento);
    }

    //ricerca la prossima parola da inserire nel cruciverba
    public String cercaParolaDaInserire(Parola casellaDaCompletare, ArrayList<String> dizionario){
        ArrayList<String> paroleDizionarioTrovate=new ArrayList<String>();
        int i=0;
        boolean parolaUguale;
        String parolaDaCompletare=casellaDaCompletare.getParola();
        int lunghezzaParolaDaCompletare = casellaDaCompletare.getLunghezza();
        /*ciclo tutte le parole del dizionario in ricerca di una o più parole che possono entrare nelle caselle a disposizione,
         * a seconda dei caratteri già inseriti,
         * se nessuna parola viene trovata si ritorna una stringa vuota
         * se ci sono più parole da poter inserire si ritorna anche qui una stringa vuota
         * se ce n'è solo una invece ritorno la stringa da inserire all'interno di queste caselle
         */

        for (String s : dizionario){
            parolaUguale=true;
            //se la lunghezza delle caselle da completare è diversa da quelle della parola s del dizionario salto il ciclo che confronta i caratteri
            // delle due parole per vedere se sono compatibili
            i=0;
            if (s.length()==lunghezzaParolaDaCompletare){
                //confronto i caratteri della parola da completare con quelle delle parole nel dizionario, se non è presente
                //nessun vincolo violato allora inserisco la parola del dizionario nella lista di parola da inserire
                while (i<lunghezzaParolaDaCompletare && parolaUguale){
                    char carattere = parolaDaCompletare.charAt(i);
                    if (carattere!='.'){
                        if (carattere!=s.charAt(i)){
                            parolaUguale=false;
                        }
                    }
                    i++;

                }
                if (parolaUguale){
                    paroleDizionarioTrovate.add(s);

                }
            }
        }
        //se ho trovato solo una parola da inserire la ritorno alla funzione chiamante
        if (paroleDizionarioTrovate.size()==1){
            return paroleDizionarioTrovate.get(0);
        }else{
            return "";
        }
    }

    //corrisponde ad un ciclo di risolviCruciverba (in cui poi viene lanciata la funzione cercaParolaDaInserire)
    //inserisce una parola nello schema del cruciverba
    public String inserisci1Parola(){
        boolean trovataParola=false;
        String parolaDaInserire=null;

        int cicliEseguiti = 0, cicliMax = 100, c, lunghezzaMax = schema_originale.cercaLunghezzaParolaMax();
        int numeroParoleLunghezzaC = 0, numeroParoleLunghezzaCInserite = 0;

        ArrayList<ArrayList<Parola>> listaParoleLunghezzaC = new ArrayList<ArrayList<Parola>>();

        //se già eseguito l'algoritmo ritorno null
        if (isAlgExecuted()){
            return null;
        }
            //inserisco all'interno della lista delle parole di lunghezza i le parole di lunghezza i
            for (int i = 0; i < lunghezzaMax; i++) {
                ArrayList<Parola> paroleLunghezzaC;
                paroleLunghezzaC = schema_originale.ricercaLunghezzaParole(i + 1);
                if (paroleLunghezzaC.size() > 0) {
                    listaParoleLunghezzaC.add(i, paroleLunghezzaC);
                } else {
                    listaParoleLunghezzaC.add(new ArrayList<Parola>());
                }
            }
            //ciclo finchè il cruciverba non è completo, fino ad aver fatto n>cicliMax iterazioni e se non trovo una parola da inserire
            while (!isComplete() && cicliEseguiti < cicliMax && !(trovataParola)) {
                cicliEseguiti++;
                c = 0;
                while (c < lunghezzaMax && !(trovataParola)) {
                    //prendo le parole di lunghezza=c su cui lavorerò
                    ArrayList<Parola> paroleLunghezzaC = listaParoleLunghezzaC.get(c);
                    int i=0;
                    while ( i < paroleLunghezzaC.size() && !(trovataParola)) {
                        Parola casellaDaCompletare = paroleLunghezzaC.get(i);
                        //prima di cercare la parolaDaInserire faccio un controllo se la parola nella lista paroleLunghezzaC è già completa,
                        // se si la elimino dalla lista
                        if (casellaDaCompletare.getLunghezza() == casellaDaCompletare.getLettereInserite()) {
                            paroleLunghezzaC.remove(casellaDaCompletare);
                        } else {
                            parolaDaInserire = cercaParolaDaInserire(casellaDaCompletare, dizionario);
                            if (!(parolaDaInserire.equals(""))) {
                                //è stata trovata una parola da inserire nello schema, rimuovo quindi la casellaDaCompletare dalla lista di parole di
                                // lunghezza c ancora da inserire
                                trovataParola=true;
                                paroleLunghezzaC.remove(casellaDaCompletare);

                                casellaDaCompletare.setParola(parolaDaInserire);
                                //aggiorno lo schema con la nuova parola trovata
                                aggiornaParola(casellaDaCompletare.getParola(), casellaDaCompletare.getPosizioneParola().getRiga()
                                        , casellaDaCompletare.getPosizioneParola().getColonna(), casellaDaCompletare.getOrientamento());

                                //aggiorno il dizionario togliendo la parola che è stata inserita nello schema
                                // + eventuali parole che si sono autocompletate inserendo una parola nello schema
                                aggiornaDizionario();
                            }else{
                                //incremento solo se la parola non era già completata o se non è stato inserito niente nello schema
                                i++;
                            }
                        }
                    }
                    c++;
                }
            }

            //controllo se il cruciverba è stato completato o meno
            if (trovataParola) {
                return parolaDaInserire;
            } else {
                risolviCruciverba();
                return null;
            }

    }

    // risoluzione cruciverba attraverso l'utilizzo dell'algoritmo 2 ritorno true se il cruciverba è stato completato, altrimenti false
    public boolean risolviCruciverba() {
        int cicliEseguiti = 0, cicliMax = 100, c, lunghezzaMax = schema_originale.cercaLunghezzaParolaMax();
        int numeroParoleLunghezzaC = 0, numeroParoleLunghezzaCInserite = 0;
        ArrayList<ArrayList<Parola>> listaParoleLunghezzaC = new ArrayList<ArrayList<Parola>>();

        //se ho già eseguito l'algoritmo di risoluzione ritorno il risultato salvato
        if(algExecuted){
            return algResult;
        }else {
            if (isComplete()) {
                algExecuted=true;
                algResult = true;
                return true;
            } else {
                //inserisco all'interno della lista delle parole di lunghezza i le parole di lunghezza i
                for (int i = 0; i < lunghezzaMax; i++) {
                    ArrayList<Parola> paroleLunghezzaC;
                    paroleLunghezzaC = schema_originale.ricercaLunghezzaParole(i + 1);
                    if (paroleLunghezzaC.size() > 0) {
                        listaParoleLunghezzaC.add(i, paroleLunghezzaC);
                    } else {
                        listaParoleLunghezzaC.add(new ArrayList<Parola>());
                    }
                }
                //ciclo finchè il cruciverba non è completo, fino ad aver fatto n>cicliMax iterazioni
                while (!isComplete() && cicliEseguiti < cicliMax) {
                    cicliEseguiti++;
                    c = 0;
                    while (c < lunghezzaMax) {
                        //prendo le parole di lunghezza=c su cui lavorerò
                        ArrayList<Parola> paroleLunghezzaC = listaParoleLunghezzaC.get(c);
                        int i = 0;
                        while (i < paroleLunghezzaC.size()) {
                            Parola casellaDaCompletare = paroleLunghezzaC.get(i);
                            //prima di cercare la parolaDaInserire faccio un controllo se la parola nella lista paroleLunghezzaC è già completa,
                            // se si la elimino dalla lista
                            if (casellaDaCompletare.getLunghezza() == casellaDaCompletare.getLettereInserite()) {
                                paroleLunghezzaC.remove(casellaDaCompletare);
                            } else {
                                String parolaDainserire = cercaParolaDaInserire(casellaDaCompletare, dizionario);
                                if (!(parolaDainserire.equals(""))) {
                                    //è stata trovata una parola da inserire nello schema, rimuovo quindi la casellaDaCompletare dalla lista di parole di
                                    // lunghezza c ancora da inserire
                                    paroleLunghezzaC.remove(casellaDaCompletare);

                                    casellaDaCompletare.setParola(parolaDainserire);
                                    //aggiorno lo schema con la nuova parola trovata
                                    aggiornaParola(casellaDaCompletare.getParola(), casellaDaCompletare.getPosizioneParola().getRiga()
                                            , casellaDaCompletare.getPosizioneParola().getColonna(), casellaDaCompletare.getOrientamento());

                                    //aggiorno il dizionario togliendo la parola che è stata inserita nello schema
                                    // + eventuali parole che si sono autocompletate inserendo una parola nello schema
                                    aggiornaDizionario();
                                } else {
                                    //incremento solo se la parola non era già completata o se non è stato inserito niente nello schema
                                    i++;
                                }
                            }
                        }
                        c++;
                    }
                }

                //setto la variabile di esecuzione algoritmo
                algExecuted = true;
                //controllo se il cruciverba è stato completato o meno e setto la variabile risultato
                if (isComplete()) {
                    algResult = true;
                    return true;
                } else {
                    algResult = false;
                    return false;
                }
            }
        }
    }

}
