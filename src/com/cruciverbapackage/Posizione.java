package com.cruciverbapackage;

public class Posizione {
    int riga;
    int colonna;

    public Posizione() {
        this.riga = -1;
        this.colonna = -1;
    }

    public Posizione(int rigaInput, int colonnaInput) {
        this.riga = rigaInput;
        this.colonna = colonnaInput;
    }

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

    public void ripristinaPosizione() {
        this.riga = -1;
        this.colonna = -1;
    }

    public boolean equals(Posizione posizioneDaConfrontare) {
        if (this.riga == posizioneDaConfrontare.getRiga() && this.colonna == posizioneDaConfrontare.getColonna()) {
            return true;
        } else {
            return false;
        }
    }
}