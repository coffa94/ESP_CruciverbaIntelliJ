package com.cruciverbapackage;

import java.util.ArrayList;

public class Variable {
    private Parola value;
    private Domain variableDomain;

    public Variable(Parola var, Domain d){
        value=new Parola(var);
        variableDomain=new Domain(d);
    }

    //procedura di inferenza sul dominio di questa variabile per rimuovere i valori non permessi
    public void inference(){
        ArrayList<String> listValuesDomain = variableDomain.getListValues();
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
                    //altrimenti incremento il valore di j e continuo sul valore del dominio successivo
                    if(checkChar!=s.charAt(i)){
                        listValuesDomain.remove(j);
                    }else {
                        j++;
                    }

                }
            }

        }
        variableDomain.setListValues(listValuesDomain);
    }

    /*
    //calcolo la percentuale di completamento della parola relativa alla variabile
    public float getPercentageInsertedLetters(){
        return (100*value.getLettereInserite()/value.getLunghezza());
    }
     */

    //ritorno il numero di lettere della variabile
    public int getNumberLetters(){
        return value.getLunghezza();
    }

    //ritorna il numero di valori dentro il dominio di questa variabile
    public int getValuesNumber(){
        return variableDomain.getValuesNumber();
    }
}
