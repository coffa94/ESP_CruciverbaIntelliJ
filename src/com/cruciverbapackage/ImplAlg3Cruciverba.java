package com.cruciverbapackage;

import javax.swing.*;
import java.util.ArrayList;

public class ImplAlg3Cruciverba {
    private Schema schema_originale;
    //private SchemaScomposto schema_scomposto;
    private ArrayList<String> dizionario;

    //costruttore cruciverba con una struttura passata in input
    public ImplAlg3Cruciverba(JPanel panel, char matrice[][], String parolaIniziale, int posizioneRigaIniziale, int posizioneColonnaIniziale, ArrayList<String> dizionarioInput) {
        schema_originale = new Schema(panel, matrice, parolaIniziale, new Posizione(posizioneRigaIniziale, posizioneColonnaIniziale), 'O');
        if (dizionarioInput != null && dizionarioInput.size() != 0) {
            dizionario = dizionarioInput;
        }

    }

    //@requires: this!=null, parola!=null, posizione all'interno dello schema
    //@modifies: this
    //@effects: inserisce la parola trovata all'interno del cruciverba
    //@throws: nullPointerException, positionException se posizione non interna al cruciverba
    //         lengthException se lunghezza parola non entra nel cruciverba
    public void aggiornaParola(String parola, int riga, int colonna, char orientamento) {
        schema_originale.aggiornaSchema(parola,new Posizione(riga,colonna),orientamento);
    }

    //@requires: this!=null, lettera!=null, posizione all'interno del cruciverba
    //@modifies: this
    //@effects: inserisce la lettera dentro il cruciverba alla posizione indicata
    //@throws: nullPointerException, positionException se posizione non interna al cruciverba
    public void aggiornaLettera(char lettera, int riga, int colonna) {

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

    //@requires: this!=null
    //@effects: risoluzione cruciverba attraverso l'utilizzo dell'algoritmo 3
    //@throws: nullPointerException
    //@return: true se completato, false se non è possibile completarlo
    public boolean risolviCruciverba() {
        //TODO questa lunghezza=10 potrebbe essere sostituiti analizzando la dimensione dell'insieme trovato cercando il numero di parole
        // suddiviso per lunghezza all'interno dello schema
        int cicliEseguiti = 0, cicliMax = 100, c, lunghezzaMax = 10;
        int numeroParoleLunghezzaC = 0, numeroParoleLunghezzaCInserite = 0;
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
        ArrayList<ArrayList<Parola>> listaParoleLunghezzaC = new ArrayList<ArrayList<Parola>>();

        if (isComplete()) {
            return true;
        } else {
            for (int i = 0; i < lunghezzaMax; i++) {
                ArrayList<Parola> paroleLunghezzaC;
                paroleLunghezzaC = schema_originale.ricercaLunghezzaParole(i + 1);
                if (paroleLunghezzaC.size() > 0) {
                    listaParoleLunghezzaC.add(i, paroleLunghezzaC);
                } else {
                    listaParoleLunghezzaC.add(new ArrayList<Parola>());
                }
            }

            while (!isComplete() && cicliEseguiti < cicliMax) {
                cicliEseguiti++;
                c = 0;
                while (c < lunghezzaMax) {
                    ArrayList<Parola> paroleLunghezzaC = listaParoleLunghezzaC.get(c);
                    int i=0;
                    while ( i < paroleLunghezzaC.size()) {
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
            if (isComplete()) {
                return true;
            } else {
                return false;
            }
        }
    }

    //@requires: this!=null
    //@effects: controllo se cruciverba è finito o no
    //@throws: nullPointerException
    //@return: true se completo, false altrimenti
    public boolean isComplete() {
        if (dizionario.size()==0){
            return true;
        }else{
            return false;
        }
    }

    //aggiornamento dizionario con le parole dello schema COMPLETATE, in questo modo quelle che parole che si sono completate automaticamente inserendo
    //altre parole nello schema vengono eliminate dal dizionario
    public void aggiornaDizionario(){
        ArrayList<Parola> paroleSchema = schema_originale.getParoleSchema();
        String parolaCorrente;
        for (Parola p : paroleSchema){
            parolaCorrente=p.getParola();
            if (p.getLunghezza()==p.getLettereInserite() && dizionario.contains(parolaCorrente)){
                dizionario.remove(parolaCorrente);
            }
        }
    }
}
