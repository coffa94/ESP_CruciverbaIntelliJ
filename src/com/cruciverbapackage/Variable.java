package com.cruciverbapackage;

//Coffaro_Davide_mat556603_Progetto ESP cruciverba

import java.util.ArrayList;

public class Variable {
    private Parola value;
    private Domain variableDomain;
    private boolean valueAssigned;
    private ArrayList<String> oldListValues;
    private Parola oldValue;

    //costruttore in cui inizializzo la parola e il dominio della variabile + altre variabili per lo stato dell'oggetto
    public Variable(Parola var, Domain d){
        value=new Parola(var);
        variableDomain=new Domain(d);
        valueAssigned=false;
        oldListValues=null;
        oldValue=null;
    }

    //costruisco una variabile da una già esistente
    public Variable(Variable var){
        this.value=var.getValue();
        this.variableDomain=var.getVariableDomain();
        this.valueAssigned=var.isValueAssigned();
        this.oldListValues=var.getOldListValues();
        this.oldValue=var.getOldValue();
    }

    public Domain getVariableDomain(){ return new Domain(this.variableDomain); }

    public Parola getValue(){ return new Parola(this.value); }

    public boolean isValueAssigned() {
        return valueAssigned;
    }

    public void setValueAssigned(boolean valueAssigned) {
        this.valueAssigned = valueAssigned;
    }

    public ArrayList<String> getOldListValues() {
        return oldListValues;
    }

    public Parola getOldValue() {
        return oldValue;
    }

    public void setOldValue(Parola oldValue) {
        this.oldValue = oldValue;
    }

    //procedura di inferenza sul dominio di questa variabile per rimuovere i valori non permessi dopo assegnazione di un valore ad un'altra variabile
    //ritorna false se il dominio risultante dall'inferenza risulta essere vuoto
    public boolean inferenceAfterAssignedValue(String s){
        ArrayList<String> listValuesDomain=variableDomain.getListValues();

        //salvo una copia della lista valore del dominio per poterla ripristinare in caso di errore di inferenza (dominio vuoto)
        oldListValues=new ArrayList<String>(listValuesDomain);
        if (listValuesDomain.contains(s)){
            listValuesDomain.remove(s);
        }

        //se il dominio dopo aver fatto inferenza non è vuoto lo aggiorno per la variabile corrente e ritorno true
        //altrimenti ritorno false senza aggiornare il dominio della variabile corrente
        if(listValuesDomain.size()>0){
            variableDomain.setListValues(listValuesDomain);
            return true;
        }else{
            return false;
        }
    }

    //procedura di inferenza sul dominio di questa variabile per rimuovere i valori non permessi dopo aggiornamento parola
    //ritorna false se il dominio risultante dall'inferenza risulta essere vuoto
    public boolean inferenceAfterUpdateParola(){
        ArrayList<String> listValuesDomain = variableDomain.getListValues();

        //salvo una copia della lista valore del dominio per poterla ripristinare in caso di errore di inferenza (dominio vuoto)
        oldListValues=new ArrayList<String>(listValuesDomain);
        String parolaSchema = value.getParola();
        for (int i=0; i<parolaSchema.length(); i++){
            int j=0;
            char checkChar=parolaSchema.charAt(i);

            //faccio la procedura di controllo carattere solo se è diverso dal carattere '.' che è quello preimpostato
            // nelle caselle dello schema
            if(checkChar!='.'){
                while(j<listValuesDomain.size()){
                    String s = listValuesDomain.get(j);

                    //se il carattere della parola è diverso da quello del valore del dominio, tolgo quel valore dal dominio di questa variabile
                    //altrimenti incremento il valore di j e continuo sul valore successivo del dominio
                    if(checkChar!=s.charAt(i)){
                        listValuesDomain.remove(j);
                    }else {
                        j++;
                    }

                }
            }

        }

        //se il dominio dopo aver fatto inferenza non è vuoto lo aggiorno per la variabile corrente e ritorno true
        //altrimenti ritorno false senza aggiornare il dominio della variabile corrente
        if(listValuesDomain.size()>0){
            variableDomain.setListValues(listValuesDomain);
            return true;
        }else{
            return false;
        }
    }

    //ritorno il numero di lettere della variabile
    public int getNumberLetters(){
        return value.getLunghezza();
    }

    //ritorna il numero di valori dentro il dominio di questa variabile
    public int getValuesNumber(){
        return variableDomain.getValuesNumber();
    }

    public String getValueDomain(int index){
        return variableDomain.getValueDomain(index);
    }

    public ArrayList<String> getListValuesDomain(){
        return variableDomain.getListValues();
    }

    //setto value con una nuova parola
    public void setNewParola(String s){
        value.setParola(s);
    }

    public void aggiornaCaselleParola(){
        value.aggiornaCaselleParola();
    }

    public boolean aggiornaParola(){
        return value.aggiornaParola();
    }

    //confronto le caselle della Parola relativa a questo oggetto con quella della variabile passata in input, se una sola
    // casella coincide allora le due variabili sono collegate e ritorno true
    public boolean isLinked(Variable var){
        return value.isLinked(var.getValue());
    }

    //ripristino i valori precedenti del dominio
    public void restoreDomain(){
        variableDomain.setListValues(oldListValues);
    }

    //ripristino i valori predefiniti del dominio (quelli iniziali)
    public void restoreDomain(Domain initialDomain){
        variableDomain.setListValues(initialDomain.getListValues());
    }

    //ripristino la parola precedente della variabile (prima dell'inferenza)
    public void ripristinaParola(){
        value=oldValue;
    }

    //ripristino i valori della variabile corrente con quelli della variabile passata in input
    public void restore(Variable oldVar, Domain initialDomain){
        this.value.setParola(oldVar.getValue().getParola());
        this.value.aggiornaCaselleParola();
        this.variableDomain.setListValues(initialDomain.getListValues());
        this.valueAssigned=oldVar.isValueAssigned();
        this.oldListValues=oldVar.getOldListValues();
        Parola restoreOldValue = oldVar.getOldValue();
        if (!(restoreOldValue==null)){
            this.oldValue.setParola(restoreOldValue.getParola());
        }

    }

}
