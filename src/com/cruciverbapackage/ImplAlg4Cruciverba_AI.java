package com.cruciverbapackage;

//Coffaro_Davide_mat556603_Progetto ESP cruciverba

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImplAlg4Cruciverba_AI extends ImplementazioneCruciverba{

    //creo inner class SizeException che estende Exception, perché viene utilizzata solo all'interno di questa implementazione
    public class SizeException extends Exception{
        public SizeException(){
            super();
        }
        public SizeException(String s){
            super(s);
        }

    }
    private CSP constraintsSolver;
    private ArrayList<Parola> listSolution;

    //costruttore cruciverba con una struttura passata in input che richiama il costruttore del padre e inizializza la variabile di tipo CPS
    public ImplAlg4Cruciverba_AI(JPanel panel, char matrice[][], String parolaIniziale, int posizioneRigaIniziale, int posizioneColonnaIniziale, ArrayList<String> dizionarioInput, char orientamento) {
        super(panel,matrice,parolaIniziale,posizioneRigaIniziale,posizioneColonnaIniziale,dizionarioInput, orientamento);
        constraintsSolver= new CSP(schema_originale,dizionario);
    }

    //corrisponde ad un ciclo di risolviCruciverba (in cui poi viene lanciata la funzione cercaParolaDaInserire)
    //inserisce una parola nello schema del cruciverba
    public String inserisci1Parola(){
        int i=0;
        Parola p=null;
        //controllo se l'algoritmo di risoluzione era già stato eseguito
        if (!(constraintsSolver.isCSPExecuted())){

            //se non era stato eseguito lo eseguo e salvo il risultato nella variabile risultato di CPS, nella listSolution sarà contenuto
            // l'ordine di inserimento delle parole nello schema
            constraintsSolver.setCSPResult(backtrackSearch(constraintsSolver));

            //se risultato dell'algoritmo è true prendo un elemento di listSolution e lo inserisco nello schema del cruciverba
            if (constraintsSolver.isCSPResult()){
                if(listSolution.size()>0){
                    p=listSolution.get(i);
                    schema_originale.aggiornaSchema(p.getParola(),p.getPosizioneParola(),p.getOrientamento());
                    listSolution.remove(i);
                }
                //aggiorno le parole disponibili nel dizionario dopo l'inserimento della nuova parola nel cruciverba
                aggiornaDizionario();
            }
        }else{
            //in questo ramo non rieseguo l'algoritmo e se il risultato dell'algoritmo è true prendo un elemento di listSolution e lo inserisco
            // nello schema del cruciverba
            if(constraintsSolver.isCSPResult()) {
                if(listSolution.size()>0){
                    p=listSolution.get(i);
                    schema_originale.aggiornaSchema(p.getParola(),p.getPosizioneParola(),p.getOrientamento());
                    listSolution.remove(i);
                }
                //aggiorno la lista delle parole disponibili nel dizionario dopo l'inserimento dell'ultima parola
                aggiornaDizionario();
            }


        }

        algResult=constraintsSolver.isCSPResult();

        if (listSolution.size()==0) {
            if (p==null){
                return null;
            }else{
                return p.getParola();
            }
        }else{
            return p.getParola();
        }
    }

    //implementazione algoritmo 4 con AI utilizzo CSP
    //CSP:
    //variabili = paroleSchema
    //domini = x domini ognuno relativo alle parole di x lettere
    //vincoli = relativi alle variabili, ad esempio la variabile in posizione 2^ riga - 3^ colonna in orizzontale
    //          ha la lettera 'i' nella quarta casella
    //backtracking cronologico = se non riesco a completare lo schema del cruciverba con l'attuale assegnamento faccio passi indietro
    //                            e provo ad inserire un altro valore nella variabile a cui avevo assegnato un valore errato
    public boolean risolviCruciverba(){
        int i=0;
        if (!(constraintsSolver.isCSPExecuted())){

            constraintsSolver.setCSPResult(backtrackSearch(constraintsSolver));

            if (constraintsSolver.isCSPResult()){
                //inserisco tutte le parole nella listSolution all'interno dello schema del cruciverba, una alla volta
                while(listSolution.size()>0){
                    Parola p=listSolution.get(i);
                    schema_originale.aggiornaSchema(p.getParola(),p.getPosizioneParola(),p.getOrientamento());
                    listSolution.remove(i);
                }
                //aggiorno la lista di parole disponibili del dizionario dopo l'inserimento delle parole di listSolution
                aggiornaDizionario();
            }
            algResult=constraintsSolver.isCSPResult();
            return algResult;
        }else{
            //se avevo già eseguito l'algoritmo inserisco solo le parole rimanenti nella listSolution all'interno dello schema del cruciverba
            if(constraintsSolver.isCSPResult()) {
                while (listSolution.size() > 0) {
                    Parola p=listSolution.get(i);
                    schema_originale.aggiornaSchema(p.getParola(),p.getPosizioneParola(),p.getOrientamento());
                    listSolution.remove(i);
                }
                //aggiorno la lista di parole disponibili dopo l'inserimento delle parole nel cruciverba
                aggiornaDizionario();
            }
            algResult=constraintsSolver.isCSPResult();
            return algResult;

        }

    }


    //lancio procedura per soluzione cruciverba con AI
    private boolean backtrackSearch( CSP csp){
        //setto variabile di esecuzione algoritmo a true
        csp.setCSPExecuted(true);
        //salvo lo stato dello schema  attuale per poterlo ripristinare dopo l'esecuzione dell'algoritmo
        //che conterrà altrimenti tutti i riferimenti modificati durante l'esecuzione dell'algoritmo
        Schema oldSchema=new Schema(schema_originale);
        backtrack(new ArrayList<Parola>(), csp);
        if (listSolution.size()==constraintsSolver.getNumberVariables()){
            //ripristino lo schema originale e anche i valori a prima dell'esecuzione dell'algoritmo nelle caselle delle parole
            schema_originale=oldSchema;
            for (Parola p : schema_originale.getParoleSchema()){
                p.aggiornaCaselleParola();
            }
            return true;
        }else{
            //ripristino lo schema originale e anche i valori a prima dell'esecuzione dell'algoritmo nelle caselle delle parole
            schema_originale=oldSchema;
            for (Parola p : schema_originale.getParoleSchema()){
                p.aggiornaCaselleParola();
            }
            return false;
        }
    }

    //ricerca soluzione cruciverba con AI con variable=MRV (minimum remaining values)+euristica del grado, value=prossimo valore del dominio ancora
    // non provato,
    // inferenza con FC (forward checking), backtracking cronologico
    // ritorno il valore null quando sono arrivato in fondo alla procedura, altrimenti ritorno la variabile che volevo utilizzare per fare il
    // backtracking intelligente sulle variabili collegate ad essa, adesso non è utile perché utilizzo il backtracking cronologico
    private Variable backtrack(ArrayList<Parola> assignment, CSP csp){
        int countAssignment=assignment.size();
        Variable varResult=null;
        if (assignment.size()==csp.getNumberVariables()){
            listSolution=assignment;
            return null;
        }
        Variable var=selectUnassignedVariable(constraintsSolver);
        if (var==null){
            //termino la procedura corrente
            //nessuna variabile a cui assegnare un valore trovata
            return null;
        }else{
            //mantengo una copia della vecchia variabile nel caso in cui l'assegnamento corrente non è corretto
            Variable oldVar = new Variable(var);

            //scorro i valori del dominio prendendoli uno ad uno dal dominio
            for(String s : orderDomainValues(var,assignment,csp)){
                var.setNewParola(s);
                var.aggiornaCaselleParola();
                var.setValueAssigned(true);
                assignment.add(var.getValue());
                countAssignment++;
                if (inference(csp,var,s)){
                    //chiamo di nuovo backtrack per trovare il prossimo assegnamento da fare
                    varResult= backtrack(assignment,csp);
                    if (varResult==null){
                        return null;
                    }
                }
                //operazioni di ripristino nel caso in cui l'inferenza non è andata a buon fine
                countAssignment--;
                assignment.remove(countAssignment);
                var=oldVar;
            }
        }
        return null;
    }


    //seleziono la variabile per la ricerca di un valore da inserire seguendo la strategia di CSP
    private Variable selectUnassignedVariable(CSP constraintsSolver) {
        //imposto il valore iniziale uguale al numero di parole da inserire nel cruciverba
        int minValues = dizionario.size();
        ArrayList<Variable> listCandidateVariables = null;

        //procedura di selezione variabili per minor valore dei domini
        for (Variable v : constraintsSolver.getVariables()) {
            int variableValues = v.getValuesNumber();
            if (!(v.isValueAssigned())) {
                if (variableValues < minValues) {
                    //se il numero di valori del sottodominio per questa variabile è inferiore a quella precedente creo una nuova lista
                    // (la lista precedente contenente le variabili con numero valori dominio maggiore viene scartata)
                    // in cui inserisco la variabile corrente e aggiorno il numero valore sottodominio minimo
                    listCandidateVariables = new ArrayList<Variable>();
                    listCandidateVariables.add(v);
                    minValues = variableValues;
                } else if (variableValues == minValues) {

                    //controllo se non era ancora stata inizializzata la lista delle variabili candidate
                    if (listCandidateVariables == null) {
                        listCandidateVariables = new ArrayList<Variable>();
                    }

                    //inserisco la variabile corrente nella lista contenente le variabili con la stessa percentuale di completamento
                    listCandidateVariables.add(v);
                }
            }
        }

        try{
            if(listCandidateVariables.size()==0){
                //se la lista contiene zero elementi sollevo un'eccezione. Inner class creata dentro questa classe perché
                // la utilizzo solo al suo interno
                throw new ImplAlg4Cruciverba_AI.SizeException("Non è stata trovata nessuna variabile candidata per l'inserimento di un nuovo valore.");

            }else if(listCandidateVariables.size()==1){
                //se la lista delle variabili candidate contiene un solo elemento lo passo alla return della funzione
                return listCandidateVariables.get(0);
            }else{
                //la lista contiene più elementi, cerco quella variabile che vincola maggiormente le altre variabili (quella con più lettere)
                int maxLetters=0;
                Variable maxLettersVariable=null;
                for (Variable v : listCandidateVariables){
                    int currentLetters=v.getNumberLetters();
                    if(currentLetters>maxLetters){
                        maxLetters=currentLetters;
                        maxLettersVariable=v;
                    }

                }
                if(maxLettersVariable!=null){
                    return maxLettersVariable;
                }else{
                    throw new NullPointerException("Non è stata trovata una variabile più vincolante dentro la lista delle variabili candidate");
                }
            }
        }catch (SizeException e){
            System.out.println(e);
            return null;
        }catch (NullPointerException e){
            System.out.println(e);
            return null;
        }
    }

    //creo la lista di assegnamenti di valori del dominio alla variabile, ordinata in ordine di inserimento nel dominio
    private ArrayList<String> orderDomainValues(Variable var, ArrayList<Parola> assignment, CSP csp){
        //TODO implementare soluzione per poter creare una lista ordinata di valori da quello meno vincolante a quello più vincolante
        //per adesso ritorno semplicemente la lista dei valori del dominio possibili
        return var.getListValuesDomain();

    }

    //procedura che mi permette di:
    // 1) togliere dal problema csp la variabile var perché gli è stato assegnato un valore
    // 2) cercare le variabili collegate a var di cui dovrò modificare il dominio dovuto alla nuova stringa s assegnata a var
    // 3) fare un controllo che i domini risultanti delle variabili collegate non siano vuoti:
    //    se lo sono ritorno false altrimenti true
    private boolean inference(CSP csp, Variable var, String s){

        ArrayList<Variable> listLinkedVariables=null;
        ArrayList<Variable> listSameLengthVariables=null;
        int counterLinkedVariables=0;
        int counterSameLengthVariables=0;
        boolean result=true;

        //trovare variabili collegate a var (cioè che condividono le stesse caselle) e ridurre il loro dominio
        listLinkedVariables=csp.searchLinkedVariables(var);
        while (listLinkedVariables!=null && counterLinkedVariables<listLinkedVariables.size() && result){
            Variable currentVar=listLinkedVariables.get(counterLinkedVariables);
            currentVar.setOldValue(currentVar.getValue());
            currentVar.aggiornaParola();
            //procedura di inference sulla variabile corrente, se va a buon fine proseguo altrimenti ripristino i valori e imposto la
            // variabile result a false
            if (!(currentVar.inferenceAfterUpdateParola())){
                currentVar.ripristinaParola();
                result=false;
            }
            counterLinkedVariables++;
        }

        //guardo adesso tutte le variabili con la stessa lunghezza della variabile corrente a cui ho assegnato il valore e rimuovo il valore
        // assegnato dal loro dominio (se presente)
        listSameLengthVariables=csp.searchSameLengthVariables(var.getNumberLetters());
        while(listSameLengthVariables!=null && counterSameLengthVariables<listSameLengthVariables.size() && result){
            Variable currentVar=listSameLengthVariables.get(counterSameLengthVariables);
            if (!(currentVar.inferenceAfterAssignedValue(s))){
                result=false;
            }
            counterSameLengthVariables++;
        }


        //se il risultato dell'inferenza è false ripristino tutti i valori dei domini che avevo modificato durante la procedura sulle variabili collegate
        //partendo dalla penultima variabile perché l'ultima su cui si era verificato l'errore di inferenza li ha già ripristinati (torno indietro di 2)
        counterLinkedVariables=counterLinkedVariables-2;
        while(!(result) && counterLinkedVariables>=0){
            Variable currentVar=listLinkedVariables.get(counterLinkedVariables);
            currentVar.ripristinaParola();
            currentVar.restoreDomain();
            counterLinkedVariables--;
        }
        //se il risultato dell'inferenza è false ripristino tutti i valori dei domini che avevo modificato durante la procedura sulle variabili con
        // la stessa lunghezza
        //partendo dalla penultima variabile perché l'ultima su cui si era verificato l'errore di inferenza li ha già ripristinati (torno indietro di 2)
        counterSameLengthVariables=counterSameLengthVariables-2;
        while(!(result) && counterSameLengthVariables>=0){
            Variable currentVar=listSameLengthVariables.get(counterSameLengthVariables);
            currentVar.ripristinaParola();
            currentVar.restoreDomain();
            counterSameLengthVariables--;
        }
        return result;
    }

}
