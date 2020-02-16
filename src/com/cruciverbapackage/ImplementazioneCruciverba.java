package com.cruciverbapackage;

import javax.imageio.plugins.tiff.ExifTIFFTagSet;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImplementazioneCruciverba implements Cruciverba {
    private Schema schema_originale;
    private SchemaScomposto schema_scomposto;
    private ArrayList<String> dizionario;

    //costruttore cruciverba con una struttura passata in input
    public ImplementazioneCruciverba(JPanel panel, char matrice[][], String parolaIniziale, int posizioneRigaIniziale, int posizioneColonnaIniziale, ArrayList<String> dizionarioInput) {
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
    //@effects: visualizza lo schema del cruciverba a video
    //@throws: nullPointerException
    //@return: componenteDaVisualizzare T (di tipo grafico??)
   /* public char[][] visualizzaSchema() {
        return schema_originale.SchemaToMatrice();
    }*/

    //@requires: this!=null
    //@effects: ricerca la prossima parola da inserire nel cruciverba
    //@throws: nullPointerException
    //@return: parola trovata E oppure null (in questo caso va gestito)
    public String cercaParolaDaInserire(){
        return "";
    }

    //@requires: this!=null
    //@effects: chiama n volte cercaParolaDaInserire finche lo schema non è completato, cioè isComplete=true
    //@throws: nullPointerException
    //@return: true se completato, false se non è possibile completarlo
    public boolean risolviCruciverba(){
        return true;
    }

    //<editor-fold desc="Funzioni algoritmo1">

    //non so se viene utilizzato
    public String cercaParola_alg1() {
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

    //risoluzione cruciverba attraverso l'utilizzo dell'algoritmo 1
    public boolean risolviCruciverba_alg1() {

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

        for (int i=0; i<lunghezzaMax;i++){
            trovato.add(false);
        }

        while(daTrovare_alg1(trovato) && cicliEseguiti<cicliMax){
            cicliEseguiti++;
            c=0;
            while(c<lunghezzaMax){
                //se ho già trovato tutte le parole con lunghezza c mi fermo e passo al numero c successivo
                if (!(trovato.get(c))){
                    ricercaParole=schema_originale.ricercaLunghezzaParole(c);
                    numeroParoleLunghezzaC=ricercaParole.size();
                    numeroParoleLunghezzaCInserite=0;
                    while(ricercaParole.size()>0){
                        Parola casellaDaCompletare=cercaParolaConPiuLettere(ricercaParole);
                        ricercaParole.remove(casellaDaCompletare);
                        String parolaDaInserire=cercaParolaDaInserire_alg1(casellaDaCompletare,dizionario);
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

        if (!(daTrovare_alg1(trovato))){
            return true;
        }else{
            return false;
        }

    }

    public boolean daTrovare_alg1(ArrayList<Boolean> trovato){
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

    public String cercaParolaDaInserire_alg1(Parola casellaDaCompletare, ArrayList<String> dizionario){
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
    //</editor-fold>

    //<editor-fold desc="Funzioni algoritmo2">
    //risoluzione cruciverba tramite algoritmo2
    public boolean risolviCruciverba_alg2() {
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
                            String parolaDainserire = cercaParolaDaInserire_alg2(casellaDaCompletare, dizionario);
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

    public String cercaParolaDaInserire_alg2(Parola casellaDaCompletare, ArrayList<String> dizionario){
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
    //</editor-fold>

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