package com.cruciverbapackage;

//Coffaro_Davide_mat556603_Progetto ESP cruciverba

import javax.swing.*;
import java.util.ArrayList;

public class ImplAlg1Cruciverba extends ImplementazioneCruciverba{

    //costruttore cruciverba con una struttura passata in input che richiama semplicemente il costruttore della classe padre
    public ImplAlg1Cruciverba(JPanel panel, char matrice[][], String parolaIniziale, int posizioneRigaIniziale, int posizioneColonnaIniziale, ArrayList<String> dizionarioInput, char orientamento) {
        super(panel,matrice,parolaIniziale,posizioneRigaIniziale,posizioneColonnaIniziale,dizionarioInput, orientamento);
    }

    // ricerca la prossima parola da inserire nel cruciverba con algoritmo1
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
                //confronto carattere per carattere quelle dell'oggetto Parola corrente con quelle del dizionario
                while (i<lunghezzaParolaDaCompletare && parolaUguale){
                    char carattere = parolaDaCompletare.charAt(i);
                    //confronto i caratteri solo se è un carattere valido (diverso da '.' impostato inizialmente)
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
        int cicliEseguiti=0,cicliMax=100,c;
        //analizzo le parole dello schema e prendo la lunghezza massima
        int lunghezzaMax=schema_originale.cercaLunghezzaParolaMax();
        int numeroParoleLunghezzaC=0,numeroParoleLunghezzaCInserite=0;
        ArrayList<Boolean> trovato = new ArrayList<Boolean>();

        ArrayList<Parola> ricercaParole;

        //se già eseguito l'algoritmo ritorno null
        if (isAlgExecuted()){
            return null;
        }
        //imposto tutto l'arrayList trovato a false
        for (int i=0; i<=lunghezzaMax;i++){
            trovato.add(false);
        }

        //ciclo finchè tutto l'arrayList trovato è uguale a true oppure fino ad arrivare a un'iterazione>cicliMax oppure se non ho trovato
        //nessuna parola al ciclo precedente
        while(daTrovare(trovato) && cicliEseguiti<cicliMax && !(trovataParola)){
            cicliEseguiti++;
            c=0;
            //ciclo da 0 fino alla lunghezza massima delle parole nello schema oppure esco se non ho trovato nessuna parola al ciclo precedente
            while(c<=lunghezzaMax && !(trovataParola)){
                //se ho già trovato tutte le parole con lunghezza c mi fermo e passo al numero c successivo
                if (!(trovato.get(c))){
                    ricercaParole=schema_originale.ricercaLunghezzaParole(c);
                    numeroParoleLunghezzaC=ricercaParole.size();
                    numeroParoleLunghezzaCInserite=0;
                    //ciclo le parole di lunghezza c finchè la lista non è vuota oppure esco quando non ho trovato una parola al ciclo precedente
                    while(ricercaParole.size()>0 && !(trovataParola)){
                        Parola casellaDaCompletare=cercaParolaConPiuLettere(ricercaParole);
                        ricercaParole.remove(casellaDaCompletare);
                        //chiamo la procedura di ricerca parola da inserire dell'algoritmo1
                        parolaDaInserire=cercaParolaDaInserire(casellaDaCompletare,dizionario);
                        if (!( parolaDaInserire.equals(""))){
                            trovataParola=true;
                            casellaDaCompletare.setParola(parolaDaInserire);
                            //procedura per aggiornare lo schema con la parola trovata
                            aggiornaParola(casellaDaCompletare.getParola(),casellaDaCompletare.getPosizioneParola().getRiga()
                                    , casellaDaCompletare.getPosizioneParola().getColonna(),casellaDaCompletare.getOrientamento());
                            numeroParoleLunghezzaCInserite++;

                            //aggiorno il dizionario togliendo la parola che è stata inserita nello schema
                            // + eventuali parole che si sono autocompletate inserendo una parola nello schema
                            aggiornaDizionario();
                        }

                    }
                    if (numeroParoleLunghezzaC==numeroParoleLunghezzaCInserite){
                        trovato.set(c,true);
                    }
                }

                //incremento il numero di caselle di cui voglio cercare le parole da inserire
                c++;
            }

        }

        //ritorno la parola se è stata trovata, altrimenti ritorno null ma lanciando prima la procedura di RisolviCruciverba
        //per vedere se è stato completato correttamente o no
        if (trovataParola){
            return parolaDaInserire;
        }else{
            risolviCruciverba();
            return null;
        }


    }

    //risoluzione cruciverba attraverso l'utilizzo dell'algoritmo 1 e ritorno se è stato completato o no
    public boolean risolviCruciverba(){

        //se è gia stato eseguito una volta non ripeto l'esecuzione ma ritorno il risultato ottenuto in precedenza
        if(algExecuted){
            return algResult;
        }else {

            int cicliEseguiti = 0, cicliMax = 100, c;
            //analizzo le parole dello schema e prendo la lunghezza massima
            int lunghezzaMax = schema_originale.cercaLunghezzaParolaMax();
            int numeroParoleLunghezzaC = 0, numeroParoleLunghezzaCInserite = 0;
            ArrayList<Boolean> trovato = new ArrayList<Boolean>();

            ArrayList<Parola> ricercaParole;

            //imposto tutto l'arrayList trovato a false
            for (int i = 0; i <= lunghezzaMax; i++) {
                trovato.add(false);
            }

            //ciclo finchè tutto l'arrayList trovato è uguale a true oppure fino ad arrivare a un'iterazione>cicliMax
            while (daTrovare(trovato) && cicliEseguiti < cicliMax) {
                cicliEseguiti++;
                c = 0;
                //ciclo da 0 fino alla lunghezza massima delle parole nello schema
                while (c <= lunghezzaMax) {
                    //se ho già trovato tutte le parole con lunghezza c mi fermo e passo al numero c successivo
                    if (!(trovato.get(c))) {
                        ricercaParole = schema_originale.ricercaLunghezzaParole(c);
                        numeroParoleLunghezzaC = ricercaParole.size();
                        numeroParoleLunghezzaCInserite = 0;
                        //ciclo le parole di lunghezza c finchè la lista non è vuota
                        while (ricercaParole.size() > 0) {
                            Parola casellaDaCompletare = cercaParolaConPiuLettere(ricercaParole);
                            ricercaParole.remove(casellaDaCompletare);
                            //chiamo la procedura di ricerca parola da inserire dell'algoritmo1
                            String parolaDaInserire = cercaParolaDaInserire(casellaDaCompletare, dizionario);
                            if (!(parolaDaInserire.equals(""))) {
                                casellaDaCompletare.setParola(parolaDaInserire);

                                //procedura per aggiornare lo schema con la parola trovata
                                aggiornaParola(casellaDaCompletare.getParola(), casellaDaCompletare.getPosizioneParola().getRiga()
                                        , casellaDaCompletare.getPosizioneParola().getColonna(), casellaDaCompletare.getOrientamento());
                                numeroParoleLunghezzaCInserite++;

                                //aggiorno il dizionario togliendo la parola che è stata inserita nello schema
                                // + eventuali parole che si sono autocompletate inserendo una parola nello schema
                                aggiornaDizionario();
                            }

                        }
                        if (numeroParoleLunghezzaC == numeroParoleLunghezzaCInserite) {
                            trovato.set(c, true);
                        }
                    }

                    //incremento il numero di caselle di cui voglio cercare le parole da inserire
                    c++;
                }

            }

            //aggiorno la variabile per sapere che ho eseguito una volta l'algoritmo di RisolviCruciverba
            algExecuted=true;
            //faccio un controllo se ho completato tutte le parole dello schema, aggiorno la variabile che contiene il risultato del cruciverba
            // e lo ritorno
            if (!(daTrovare(trovato))){
                algResult=true;
                return algResult;
            }else{
                algResult=false;
                return algResult;
            }
        }

    }

    //controllo se sono presenti valori a false dentro l'arrayList trovato, in quel caso significa che ancora non ho trovato tutte
    //le parole di lunghezza i
    public boolean daTrovare(ArrayList<Boolean> trovato){
        int i=0;
        try {
            if (trovato!=null){
                while(i<trovato.size()){
                    if (trovato.get(i)){
                        i++;
                    }else{
                        return true;
                    }
                }
            }else{
                throw new NullPointerException("Trovato è null");
            }

        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(101);
        }
        return false;

    }

    //cerco la parola all'interno della lista che ha più lettere già inserite, a parità di lettere già inserite prendo la prima che ho trovato
    public Parola cercaParolaConPiuLettere(ArrayList<Parola> listaParole){
        int maxLettereInserite=-1, contatoreLettereInserite=0;
        Parola maxParolaLettereInserite=null;
        for (Parola p : listaParole){
            contatoreLettereInserite=p.getLettereInserite();
            if (contatoreLettereInserite>maxLettereInserite){
                maxLettereInserite=contatoreLettereInserite;
                maxParolaLettereInserite=p;
            }
        }
        return maxParolaLettereInserite;
    }

}
