package com.cruciverbapackage;

import java.util.ArrayList;

public class Variable {
    private Parola value;
    private Domain variableDomain;

    public Variable(Parola var, Domain d){
        value=new Parola(var);
        variableDomain=new Domain(d);
    }

    //TODO inferenza su dominio della variabile
}
