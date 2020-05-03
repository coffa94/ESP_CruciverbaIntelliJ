package com.cruciverbapackage;

//Coffaro_Davide_mat556603_Progetto ESP cruciverba

public class Posizione {
    int riga;
    int colonna;

    //costruttore posizione senza valori in input, inserisco valori standard
    public Posizione() {
        this.riga = -1;
        this.colonna = -1;
    }

    //costruttore posizione a partire da valori in input
    public Posizione(int rigaInput, int colonnaInput) {
        this.riga = rigaInput;
        this.colonna = colonnaInput;
    }

    //costruttore posizione da un'altra posizione già esistente
    public Posizione(Posizione posizioneInput) {
        this.riga = posizioneInput.getRiga();
        this.colonna = posizioneInput.getColonna();
    }

    public int getRiga() {
        return riga;
    }

    public void setRiga(int riga) {
        this.riga = riga;
    }

    public int getColonna() {
        return colonna;
    }

    public void setColonna(int colonna) {
        this.colonna = colonna;
    }

    //ripristina valori di default
    public void ripristinaPosizione() {
        this.riga = -1;
        this.colonna = -1;
    }

    //controlla se la posizione passata in input corrisponde alla stessa riga e colonna dell'oggetto corrente
    public boolean equals(Posizione posizioneDaConfrontare) {
        if (this.riga == posizioneDaConfrontare.getRiga() && this.colonna == posizioneDaConfrontare.getColonna()) {
            return true;
        } else {
            return false;
        }
    }
}