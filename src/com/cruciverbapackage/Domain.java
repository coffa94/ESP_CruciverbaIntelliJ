package com.cruciverbapackage;

//Coffaro_Davide_mat556603_Progetto ESP cruciverba

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
        return new ArrayList<String>(listValues);
    }

    //modifica la lista di valori inseriti in questo dominio
    public void setListValues(ArrayList<String> listValues) {
        this.listValues = listValues;
    }

    //ritorna la lunghezza delle parole inserite in questo dominio
    public int getLunghezzaParole(){
        return length;
    }

    //aggiunge una nuova parola nella lista di valori in questo dominio
    public void add (String s){
        listValues.add(s);
    }

    //conta il numero di valori all'interno del dominio
    public int getValuesNumber(){
        return listValues.size();
    }

    //restituisco la stringa alla posizione index della lista di valori (listValues)
    public String getValueDomain(int index){
        return listValues.get(index);
    }


}