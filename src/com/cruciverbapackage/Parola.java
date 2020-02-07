package com.cruciverbapackage;

public class Parola {
    String parola;
    Posizione posizioneParola;
    int  lunghezza;
    char orientamento;

    //metodo costruttore
    //@effects: crea una parola con le relative informazioni
    public Parola(String parola, Posizione posizioneIniziale, char orientamento, int lunghezza) {
        try {
            //controllo parola
            if (parola.length()>0) {
                this.parola=parola;
            }else {
                throw new Exception("Parola non corretta.");
            }

            //controllo posizione
            if (posizioneIniziale.getRiga()>=0 && posizioneIniziale.getColonna()>=0) {
                posizioneParola.setRiga(posizioneIniziale.getRiga());
                posizioneParola.setColonna(posizioneIniziale.getColonna());
            }else {
                throw new Exception("Posizione indicata non corretta.");
            }

            //controllo orientamento
            if (orientamento=='V' || orientamento=='O') {
                this.orientamento=orientamento;
            }else {
                throw new Exception("Orientamento (verticale o orizzontale) non corretto.");
            }

            //controllo lunghezza
            if (lunghezza>1) {
                this.lunghezza=lunghezza;
            }else {
                throw new Exception("Lunghezza non corretta. Vengono inserite le parole con almeno 2 lettere");
            }
        }catch(Exception e) {
            System.out.println(e);
        }

    }



    public String getParola() {
        return parola;
    }



    public void setParola(String parola) {
        this.parola = parola;
    }



    public Posizione getPosizioneParola() {
        return posizioneParola;
    }



    public void setPosizioneParola(Posizione posizioneParola) {
        this.posizioneParola = posizioneParola;
    }



    public int getLunghezza() {
        return lunghezza;
    }



    public void setLunghezza(int lunghezza) {
        this.lunghezza = lunghezza;
    }



    public char getOrientamento() {
        return orientamento;
    }



    public void setOrientamento(char orientamento) {
        this.orientamento = orientamento;
    }



    public boolean ConfrontaCaselle(Parola p) {
        //controlla se le due parole corrispondono alla stessa casella nello schema, sia come orinetamento che come inizio e lunghezza parola
        if ( this.orientamento==p.orientamento && posizioneParola.equals(p.getPosizioneParola()) && this.lunghezza==p.lunghezza) {
            return true;
        }else {
            return false;
        }

    }
}