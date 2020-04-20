package com.cruciverbapackage;

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

    public boolean isCSPExecuted() {
        return CSPExecuted;
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
            //se è il primo elemento .... no incorpora insieme a - se è il dominio con queste x lettere esiste già o no

            /*if (domains.size()==0){
                newD = new Domain(s,s.length());
                domains.add(newD);
            }else{
                for (Domain d : domains){
                    if (d.getLunghezzaParole() == s.length()){
                        d.add(s);
                    }
                }
            }*/

            //cerco dominio con elementi di lunghezza s.length()
            foundD=searchDomain(s.length());

            //significa che non esisteva già un dominio con valori della lunghezza stringa e quindi creo un nuovo dominio,
            // ci inserisco la stringa e lo inserisco nella lista dei domini
            if (foundD==null){
                foundD=new Domain(s,s.length());
                domains.add(foundD);
            }else{                   //significa che esisteva già un dominio con valori della lunghezza stringa e quindi inserisco in coda
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


                //TODO se lettereInserite!=0 devo lanciare la procedura di inferenza sui domini delle variabili che creo?
                if (foundD!=null){
                    Variable v = new Variable(p,foundD);
                    variables.add(v);
                    //se lettereInserite per questa parola è diverso da zero lancio anche la procedura di inferenza per ridurre
                    // i domini di questa variabile
                    if (p.getLettereInserite()>0){
                        v.inference();
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

    public void solve(){
        //CSP:
        //variabili = paroleSchema
        //domini = x domini ognuno relativo alle parole di x lettere
        //vincoli = relativi alle variabili, ad esempio la variabile in posizione 2^ riga - 3^ colonna in orizzontale
        //          ha la lettera 'i' nella quarta casella
        //backtracking intelligente = gestisco la lista dei conflitti cioè quali variabili vincolano la scelta successiva
        //                            di altre variabili con caselle collegate, nel caso di dominio vuoto utilizzo la lista
        //                            dei conflitti (politica LIFO) per assegnare un nuovo valore a quella variabile.
    }

    //ritorno il numero di variabili totali
    public int getNumberVariables(){
        return variables.size();
    }
}
