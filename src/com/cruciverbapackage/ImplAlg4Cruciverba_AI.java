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
    public ImplAlg4Cruciverba_AI(JPanel panel, char matrice[][], String parolaIniziale, int posizioneRigaIniziale, int posizioneColonnaIniziale, ArrayList<String> dizionarioInput) {
        super(panel,matrice,parolaIniziale,posizioneRigaIniziale,posizioneColonnaIniziale,dizionarioInput);
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
        listSolution= backtrackSearch( constraintsSolver);
        if (listSolution.size()==constraintsSolver.getNumberVariables()){
            return true;
        }else{
            return false;
        }
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
    private ArrayList<Parola> backtrackSearch( CSP csp){
        return backtrack(new ArrayList<Parola>(), csp);
    }

    //ricerca soluzione cruciverba con AI
    private ArrayList<Parola> backtrack(ArrayList<Parola> assignment, CSP csp){
        if (assignment.size()==csp.getNumberVariables()){
            return assignment;
        }
        Variable var=selectUnassignedVariables(constraintsSolver);

        return null;
    }


    //seleziono la variabile per la ricerca di un valore da inserire seguendo la strategia di CSP
    private Variable selectUnassignedVariables(CSP constraintsSolver) {
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
            if (variableValues<minValues) {
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

        try{
            if(listCandidateVariables.size()==0){
                //se la lista contiene zero elementi sollevo un'eccezione. Inner class creata dentro questa classe perché
                // la utilizzo solo al suo interno
                throw new ImplAlg4Cruciverba_AI.SizeException("Non è stata trovata nessuna variabile candidata per l'inserimento di un nuovo valore.");

            }else if(listCandidateVariables.size()==1){
                //se la lista delle variabili candidate contiene un solo elemento lo passo alla return della funzione
                return listCandidateVariables.get(1);
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


}
