package com.cruciverbapackage;

import java.util.ArrayList;

//classe dei domini contenente le parole del dizionario suddivise per lunghezza parole
class Domain{
    private ArrayList<String> listValues;
    private int length;

    public Domain(String s, int dim){
        listValues=new ArrayList<String>();
        listValues.add(s);
        length=dim;
    }

    public Domain(Domain d){
        listValues=d.getListValues();
        length=d.getLunghezzaParole();
    }

    //ritorna la lista di valori inseriti in questo dominio
    public ArrayList<String> getListValues(){
        return listValues;
    }


    //ritorna la lunghezza delle parole inserite in questo dominio
    public int getLunghezzaParole(){
        return length;
    }

    //aggiunge una nuova parola nella lista di valori in questo dominio
    public void add (String s){
        listValues.add(s);
    }


}