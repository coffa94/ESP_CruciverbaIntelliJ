package com.cruciverbapackage;

//Coffaro_Davide_mat556603_Progetto ESP cruciverba

import java.util.ArrayList;


//classe CSP = costraintsSatisfactionProblem, utilizzo di algoritmo di AI per la ricerca della soluzione del cruciverba
public class CSP {
    private ArrayList<Parola> listSolution;
    private boolean CSPExecuted;
    private boolean CSPResult;
    private ArrayList<Variable> variables;
    private ArrayList<Domain> domains;

    public ArrayList<Parola> getListSolution() {
        return new ArrayList<Parola>(listSolution);
    }

    public ArrayList<Variable> getVariables() {
        return new ArrayList<Variable>(variables);
    }

    public void setCSPExecuted(boolean CSPExecuted) {
        this.CSPExecuted = CSPExecuted;
    }

    public boolean isCSPExecuted() {
        return CSPExecuted;
    }

    public void setCSPResult(boolean CSPResult) {
        this.CSPResult = CSPResult;
    }

    public boolean isCSPResult() {
        return CSPResult;
    }

    public CSP(Schema s, ArrayList<String> d){
        listSolution= new ArrayList<Parola>();
        CSPExecuted=false;
        CSPResult=false;
        variables= new ArrayList<Variable>();
        domains=new ArrayList<Domain>();
        insertValuesInDomain(d);
        insertValuesInVariables(s.getParoleSchema());
    }

    //inserisce tutte le stringhe all'interno del dominio con lettere corrispondenti
    private void insertValuesInDomain(ArrayList<String> dizionario){
        Domain foundD=null;
        for (String s : dizionario){
                        //cerco dominio con elementi di lunghezza s.length()
            foundD=searchDomain(s.length());

            if (foundD==null){
                //significa che non esisteva già un dominio con valori della lunghezza stringa e quindi creo un nuovo dominio,
                // ci inserisco la stringa e lo inserisco nella lista dei domini
                foundD=new Domain(s,s.length());
                domains.add(foundD);
            }else{
                //significa che esisteva già un dominio con valori della lunghezza stringa e quindi inserisco in coda
                foundD.add(s);
            }
        }
    }

    public void insertValuesInVariables(ArrayList<Parola> list){
        Domain foundD=null;
        for (Parola p : list){
            int i= 0;
            if (!(p.isComplete())){      // se la parola non è già completa
                foundD=searchDomain(p.getLunghezza());

                //se lettereInserite!=0 devo lanciare la procedura di inferenza sui domini delle variabili che creo
                if (foundD!=null){
                    Variable v = new Variable(p,foundD);
                    variables.add(v);
                    //se lettereInserite per questa parola è diverso da zero lancio anche la procedura di inferenza per ridurre
                    // i domini di questa variabile
                    if (p.getLettereInserite()>0){
                        v.inferenceAfterUpdateParola();
                    }
                }else{
                    throw new NullPointerException("Non è stato trovato un dominio per questa variabile");
                }
            }

        }
    }

    // cerco il dominio contenente elementi di lunghezza l
    public Domain searchDomain(int l){
        Domain foundD=null;
        int i=0;
        while (i<domains.size() && foundD==null){        //esco dal while se ho scorso tutto l'array dei domini o se ho trovato un elemento
            Domain checkD=domains.get(i);
            if(checkD.getLunghezzaParole()==l){
                foundD=checkD;
            }
            i++;
        }
        return foundD;
    }

    //ritorna le variabili collegata alla variabile passata in input var
    public ArrayList<Variable> searchLinkedVariables(Variable var){
        ArrayList<Variable> linkedVariables=null;

        //scorro le variabili dello schema alla ricerca di quelle collegata a quella in input
        for(Variable searchVar : variables){
            //faccio un controllo se è già assegnato un valore, in questo modo evito di verificare il collegamento con una variabile
            // alla quale ho già assegnato un valore (sia variabile corrente che altre variabili all'interno dello schema)
            if (!(searchVar.isValueAssigned())) {
                if (searchVar.isLinked(var)) {
                    if (linkedVariables==null){
                        linkedVariables=new ArrayList<Variable>();
                    }
                    linkedVariables.add(searchVar);
                }
            }
        }
        return linkedVariables;
    }

    //ritorna le variabili con la stessa lunghezza della variabile passata in input var
    public ArrayList<Variable> searchSameLengthVariables(int lengthToCompare){
        ArrayList<Variable> sameLengthVariables=null;

        //scorro le variabili dello schema alla ricerca di quelle con la stessa lunghezza di quella in input
        for(Variable searchVar : variables){
            //faccio un controllo se è già assegnato un valore, in questo modo evito di verificare il collegamento con una variabile
            // alla quale ho già assegnato un valore (sia variabile corrente che altre variabili all'interno dello schema)
            if (!(searchVar.isValueAssigned())) {
                if (searchVar.getNumberLetters()==lengthToCompare) {
                    if (sameLengthVariables==null){
                        sameLengthVariables=new ArrayList<Variable>();
                    }
                    sameLengthVariables.add(searchVar);
                }
            }
        }
        return sameLengthVariables;
    }

    //ritorno il numero di variabili totali
    public int getNumberVariables(){
        return variables.size();
    }

}
