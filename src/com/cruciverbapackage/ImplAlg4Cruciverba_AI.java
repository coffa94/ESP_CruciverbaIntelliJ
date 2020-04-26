package com.cruciverbapackage;

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

    //costruttore cruciverba con una struttura passata in input
    public ImplAlg4Cruciverba_AI(JPanel panel, char matrice[][], String parolaIniziale, int posizioneRigaIniziale, int posizioneColonnaIniziale, ArrayList<String> dizionarioInput, char orientamento) {
        super(panel,matrice,parolaIniziale,posizioneRigaIniziale,posizioneColonnaIniziale,dizionarioInput, orientamento);
        constraintsSolver= new CSP(schema_originale,dizionario);
    }

    //corrisponde ad un ciclo di risolviCruciverba (in cui poi viene lanciata la funzione cercaParolaDaInserire)
    //@requires this!=null
    //@effects: inserisce una parola nello schema del cruciverba
    //@throws: nullPointerException
    //*return: true se cruciverba è completo, false se non è stato completato o non è stata trovata una parola da inserire
    public boolean inserisci1Parola(){ return true; }

    //TODO implementare algoritmo 4 con AI utilizzo CSP
    //@requires: this!=null
    //@effects: chiama cercaParolaDaInserire finche lo schema non è completato, cioè isComplete=true
    //@throws: nullPointerException
    //@return: true se completato, false se non è possibile completarlo
    public boolean risolviCruciverba(){
        return backtrackSearch(constraintsSolver);
/*vecchia implementazione
        constraintsSolver.solve();
        if (!(constraintsSolver.isCSPExecuted())){


            if (constraintsSolver.isCSPResult()){
                listSolution=constraintsSolver.getListSolution();
                while(listSolution.size()>0){

                }
            }
            return constraintsSolver.isCSPResult();
        }else{
            if(constraintsSolver.isCSPResult()) {
                listSolution=constraintsSolver.getListSolution();
                while (listSolution.size() > 0) {

                }
            }
            return constraintsSolver.isCSPResult();

        }
*/

    }


    //lancio procedura per soluzione cruciverba con AI
    private boolean backtrackSearch( CSP csp){
        Schema oldSchema=new Schema(schema_originale);
        backtrack(new ArrayList<Parola>(), csp);
        if (listSolution.size()==constraintsSolver.getNumberVariables()){
            schema_originale=oldSchema;
            return true;
        }else{
            schema_originale=oldSchema;
            return false;
        }
    }

    //ricerca soluzione cruciverba con AI con variable=MRV (minimum remaining values)+euristica del grado, value=meno vincolante,
    // inferenza con FC (forward checking), backtracking intelligente con conflict-directed backjumping
    // ritorno il valore null quando sono arrivato in fondo alla procedura, altrimenti ritorno la variabile di cui analizzo la lista
    // delle variabili da cui dipende per fare il backtracking intelligente
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

            //scorro i valori del dominio scegliendo per primi i valori meno vincolanti, scelta di un valore che diminuisce in modo
            // minore il dominio delle variabili collegate alla variabile corrente
            for(String s : orderDomainValues(var,assignment,csp)){
                var.setNewParola(s);
                var.aggiornaCaselleParola();
                var.setValueAssigned(true);
                assignment.add(var.getValue());
                countAssignment++;
                if (inference(csp,var,s)){
                    //TODO aggiornare i domini con le stesse lettere di quella di var di cui ho appena inserito un valore

                    //chiamo di nuovo backtrack per trovare il prossimo assegnamento da fare
                    varResult= backtrack(assignment,csp);
                    if (varResult==null){
                        return null;
                    }
                }
                assignment.remove(countAssignment);
                countAssignment--;
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

        //da eliminare - vecchia procedura che prendeva percentuale di completamento parola per selezionare le variabili
        /*float maxPercentage = 0f;
        for (Variable v : constraintsSolver.getVariables()) {
            float variablePercentage = v.getPercentageInsertedLetters();
            if (variablePercentage > maxPercentage) {
                //se la percentuale trovata per questa variabile è superiore a quella precedente creo una nuova lista
                // (la lista precedente contenente le variabili con percentuale minore viene scartata) in cui inserisco la variabile corrente
                // e aggiorno la percentuale massima
                listCandidateVariables = new ArrayList<Variable>();
                listCandidateVariables.add(v);
                maxPercentage = variablePercentage;
            } else if (variablePercentage == maxPercentage) {

                //controllo se non era ancora stata inizializzata la lista delle variabili candidate
                if (listCandidateVariables == null) {
                    listCandidateVariables = new ArrayList<Variable>();
                }

                //inserisco la variabile corrente nella lista contenente le variabili con la stessa percentuale di completamento
                listCandidateVariables.add(v);
            }
        }*/

        //procedura di selezione variabili per minor valore dei sottodomini
        for (Variable v : constraintsSolver.getVariables()) {
            int variableValues = v.getValuesNumber();
            if (!(v.isValueAssigned())) {
                if (variableValues < minValues) {
                    //se il numero di valori del sottodominio per questa variabile è inferiore a quella precedente creo una nuova lista
                    // (la lista precedente contenente le variabili con numero valori sottodominio maggiore viene scartata)
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

    //creo la lista di assegnamenti di valori del dominio alla variabile, ordinata dai valori meno vincolanti a quelli più vincolanti
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

        //constraintsSolver.removeVariable(var);    //utilizzo un booleano per conoscere se è già stata assegnata o no

        //TODO accorpare le due funzioni di ricerca variabili (collegate e stessa lunghezza in un'unica procedura per non dover scorrere
        // l'array variables di csp 2 volte
        ArrayList<Variable> listLinkedVariables=null;
        ArrayList<Variable> listSameLengthVariables=null;
        int counterLinkedVariables=0;
        int counterSameLengthVariables=0;
        boolean result=true;

        //trovare variabili collegate a var e ridurre il loro dominio
        listLinkedVariables=csp.searchLinkedVariables(var);
        while (listLinkedVariables!=null && counterLinkedVariables<listLinkedVariables.size() && result){
            Variable currentVar=listLinkedVariables.get(counterLinkedVariables);
            currentVar.setOldValue(currentVar.getValue());
            currentVar.aggiornaParola();
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
            //currentVar.setOldValue(currentVar.getValue());
            //currentVar.aggiornaParola();
            if (!(currentVar.inferenceAfterAssignedValue(s))){
                result=false;
            }
            counterSameLengthVariables++;
        }


        //se il risultato dell'inferenza è false ripristino tutti i valori dei domini che avevo modificato durante la procedura sulle variabili collegate
        //partendo dal penultimo variabile perché l'ultima su cui si era verificato l'errore di inferenza li ha già ripristinati
        while(!(result) && counterLinkedVariables-1>=0){
            Variable currentVar=listLinkedVariables.get(counterLinkedVariables);
            currentVar.ripristinaParola();
            currentVar.restoreDomain();
            counterLinkedVariables--;
        }
        //se il risultato dell'inferenza è false ripristino tutti i valori dei domini che avevo modificato durante la procedura sulle variabili con
        // la stessa lunghezza
        //partendo dal penultimo variabile perché l'ultima su cui si era verificato l'errore di inferenza li ha già ripristinati
        while(!(result) && counterSameLengthVariables-1>=0){
            Variable currentVar=listSameLengthVariables.get(counterSameLengthVariables);
            currentVar.ripristinaParola();
            currentVar.restoreDomain();
            counterSameLengthVariables--;
        }
        return result;
    }

}
