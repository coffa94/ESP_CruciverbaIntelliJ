package com.cruciverbapackage;

import javax.swing.*;
import java.util.ArrayList;

public class Parola {
    private String parola;
    private ArrayList<Casella> caselleParola;
    private Posizione posizioneParola;
    private int lunghezza;
    private char orientamento;

    //metodo costruttore
    //@effects: crea una parola con le relative informazioni
    public Parola(String parola, Posizione posizioneIniziale, char orientamento, int lunghezza) {
        try {
            //controllo parola
            if (parola.length() > 0) {
                this.parola = parola;
            } else {
                throw new Exception("Parola non corretta.");
            }

            //controllo posizione
            if (posizioneIniziale.getRiga() >= 0 && posizioneIniziale.getColonna() >= 0) {
                posizioneParola = new Posizione(posizioneIniziale);
            } else {
                throw new Exception("Posizione indicata non corretta.");
            }

            //controllo orientamento
            if (orientamento == 'V' || orientamento == 'O') {
                this.orientamento = orientamento;
            } else {
                throw new Exception("Orientamento (verticale o orizzontale) non corretto.");
            }

            //controllo lunghezza
            if (lunghezza > 1) {
                this.lunghezza = lunghezza;
            } else {
                throw new Exception("Lunghezza non corretta. Vengono inserite le parole con almeno 2 lettere");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Errore", JOptionPane.ERROR_MESSAGE);
        }

    }

    //metodo costruttore
    //@effects: crea una parola con le relative informazioni
    public Parola(String parola, Posizione posizioneIniziale, char orientamento, int lunghezza, ArrayList<Casella> caselleParolaInput) {
        try {

            //inizializzo le caselle della parola con le caselle dello schema create in precedenza
            if (caselleParolaInput != null) {
                caselleParola = caselleParolaInput;
            }
            //controllo parola
            if (parola.length() > 0) {
                this.parola = parola;
            } else {
                throw new Exception("Parola non corretta.");
            }

            //controllo posizione
            if (posizioneIniziale.getRiga() >= 0 && posizioneIniziale.getColonna() >= 0) {
                posizioneParola = new Posizione(posizioneIniziale);
            } else {
                throw new Exception("Posizione indicata non corretta.");
            }

            //controllo orientamento
            if (orientamento == 'V' || orientamento == 'O') {
                this.orientamento = orientamento;
            } else {
                throw new Exception("Orientamento (verticale o orizzontale) non corretto.");
            }

            //controllo lunghezza
            if (lunghezza > 1) {
                this.lunghezza = lunghezza;
            } else {
                throw new Exception("Lunghezza non corretta. Vengono inserite le parole con almeno 2 lettere");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Errore", JOptionPane.ERROR_MESSAGE);
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
        //controlla se le due parole corrispondono alla stessa casella nello schema, sia come orientamento che come inizio e lunghezza parola
        if (this.orientamento == p.orientamento && posizioneParola.equals(p.getPosizioneParola()) && this.lunghezza == p.lunghezza) {
            return true;
        } else {
            return false;
        }

    }

    //aggiorna il testo delle caselle con la stringa dentro parola
    public void aggiornaCaselleParola(){
        try{
            if (lunghezza==caselleParola.size()){
                for (int i=0; i<lunghezza; i++){
                    caselleParola.get(i).aggiornaCarattere(parola.charAt(i));
                }
            }else{
                throw new Exception ("Lunghezza parola diversa dalla lunghezza della parola nel cruciverba");
            }
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}