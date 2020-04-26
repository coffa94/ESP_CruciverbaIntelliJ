package com.cruciverbapackage;

import javax.swing.*;
import java.util.ArrayList;

public class ImplAlg1Cruciverba extends ImplementazioneCruciverba{

    //costruttore cruciverba con una struttura passata in input
    public ImplAlg1Cruciverba(JPanel panel, char matrice[][], String parolaIniziale, int posizioneRigaIniziale, int posizioneColonnaIniziale, ArrayList<String> dizionarioInput, char orientamento) {
        super(panel,matrice,parolaIniziale,posizioneRigaIniziale,posizioneColonnaIniziale,dizionarioInput, orientamento);


    }

    //@requires: this!=null
    //@effects: ricerca la prossima parola da inserire nel cruciverba
    //@throws: nullPointerException
    //@return: parola trovata E oppure null (in questo caso va gestito)
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
                //TODO confrontare stringhe con una funzione delle stringhe??
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
        if (paroleDizionarioTrovate.size()==1){
            return paroleDizionarioTrovate.get(0);
        }else{
            return "";
        }
    }

    //corrisponde ad un ciclo di risolviCruciverba (in cui poi viene lanciata la funzione cercaParolaDaInserire)
    //@requires this!=null
    //@effects: inserisce una parola nello schema del cruciverba
    //@throws: nullPointerException
    //*return: true se cruciverba è completo, false se non è stato completato o non è stata trovata una parola da inserire
    public boolean inserisci1Parola(){
        boolean trovataParola=false;

        if (isComplete()){
            return true;
        }

        //TODO questa lunghezza=10 potrebbe essere sostituiti analizzando la dimensione dell'insieme trovato cercando il numero di parole
        // suddiviso per lunghezza all'interno dello schema
        int cicliEseguiti=0,cicliMax=100,c,lunghezzaMax=10;
        int numeroParoleLunghezzaC=0,numeroParoleLunghezzaCInserite=0;
        ArrayList<Boolean> trovato = new ArrayList<Boolean>();

        ArrayList<Parola> ricercaParole;

        for (int i=0; i<=lunghezzaMax;i++){
            trovato.add(false);
        }

        while(daTrovare(trovato) && cicliEseguiti<cicliMax && !(trovataParola)){
            cicliEseguiti++;
            c=0;
            while(c<=lunghezzaMax && !(trovataParola)){
                //se ho già trovato tutte le parole con lunghezza c mi fermo e passo al numero c successivo
                if (!(trovato.get(c))){
                    ricercaParole=schema_originale.ricercaLunghezzaParole(c);
                    numeroParoleLunghezzaC=ricercaParole.size();
                    numeroParoleLunghezzaCInserite=0;
                    while(ricercaParole.size()>0 && !(trovataParola)){
                        Parola casellaDaCompletare=cercaParolaConPiuLettere(ricercaParole);
                        ricercaParole.remove(casellaDaCompletare);
                        String parolaDaInserire=cercaParolaDaInserire(casellaDaCompletare,dizionario);
                        if (!( parolaDaInserire.equals(""))){
                            trovataParola=true;
                            casellaDaCompletare.setParola(parolaDaInserire);

                            //TODO non si può fare in modo di passare la parola di tipo Parola invece che le singole informazioni?
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

        if (isComplete()){
            return true;
        }else{
            return false;
        }


    }

    //@requires: this!=null
    //@effects: risoluzione cruciverba attraverso l'utilizzo dell'algoritmo 1
    //@throws: nullPointerException
    //@return: true se completato, false se non è possibile completarlo
    public boolean risolviCruciverba(){

        if (isComplete()){
            return true;
        }

        //TODO questa lunghezza=10 potrebbe essere sostituiti analizzando la dimensione dell'insieme trovato cercando il numero di parole
        // suddiviso per lunghezza all'interno dello schema
        int cicliEseguiti=0,cicliMax=100,c,lunghezzaMax=10;
        int numeroParoleLunghezzaC=0,numeroParoleLunghezzaCInserite=0;
        ArrayList<Boolean> trovato = new ArrayList<Boolean>();
        /*Timer tempoDiEsecuzione= new Timer(600, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    throw new Exception("Tempo massimo di esecuzione raggiunto");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.toString(), "Errore", JOptionPane.ERROR_MESSAGE);
                    System.exit(102);
                }
            }
        });*/

        ArrayList<Parola> ricercaParole;

        for (int i=0; i<=lunghezzaMax;i++){
            trovato.add(false);
        }

        while(daTrovare(trovato) && cicliEseguiti<cicliMax){
            cicliEseguiti++;
            c=0;
            while(c<=lunghezzaMax){
                //se ho già trovato tutte le parole con lunghezza c mi fermo e passo al numero c successivo
                if (!(trovato.get(c))){
                    ricercaParole=schema_originale.ricercaLunghezzaParole(c);
                    numeroParoleLunghezzaC=ricercaParole.size();
                    numeroParoleLunghezzaCInserite=0;
                    while(ricercaParole.size()>0){
                        Parola casellaDaCompletare=cercaParolaConPiuLettere(ricercaParole);
                        ricercaParole.remove(casellaDaCompletare);
                        String parolaDaInserire=cercaParolaDaInserire(casellaDaCompletare,dizionario);
                        if (!( parolaDaInserire.equals(""))){
                            casellaDaCompletare.setParola(parolaDaInserire);

                            //TODO non si può fare in modo di passare la parola di tipo Parola invece che le singole informazioni?
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

        if (!(daTrovare(trovato))){
            return true;
        }else{
            return false;
        }


    }

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

    //non so se viene utilizzato
    public String cercaParola() {
        ArrayList<Parola> ricercaParole;
        //TODO al momento dell'inserimento delle parole nello schema salvare con una variabile la lunghezza della parola più lunga.
        // Al momento prendo una lunghezzaMax fittizia.
        int lunghezzaMax = 10;

        int i=1;
        boolean trovato=false;

        try{
            //TODO sono alla ricerca della prossima parola da inserire nello schema, dopo aver cliccato il pulsante cercaParola,
            // lavoro finche non ne trovo una oppure fino alla lunghezzaMax delle caselle, il che significa che non ne ho trovata nessuna
            while (i<lunghezzaMax && !(trovato)){
                ricercaParole=schema_originale.ricercaLunghezzaParole(i);
                if (ricercaParole.size()>0){

                }
            }
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString(), "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(103);
        }
        finally{
            return "";
        }

    }

    //TODO è possibile fare count dei caratteri di una parola non contando il carattere '.'???
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
