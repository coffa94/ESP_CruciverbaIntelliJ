package com.cruciverbapackage;

//Coffaro_Davide_mat556603_Progetto ESP cruciverba

import javax.swing.*;
import java.util.ArrayList;

public class Parola {
    private String parola;
    private ArrayList<Casella> caselleParola;
    private Posizione posizioneParola;
    private int lunghezza;
    private int lettereInserite;
    private char orientamento;

    //metodo costruttore crea una parola con le relative informazioni, senza caselleParola collegate
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

    //metodo costruttore crea una parola con le relative informazioni con le caselleParola collegate
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

    //metodo costruttore crea una parola a partire da una passata in input
    public Parola(Parola p){
        this.setParola(p.getParola());
        this.setCaselleParola(p.getCaselleParola());
        this.setPosizioneParola(p.getPosizioneParola());
        this.setLunghezza(p.getLunghezza());
        this.setLettereInserite(p.getLettereInserite());
        this.setOrientamento(p.getOrientamento());
    }

    public ArrayList<Casella> getCaselleParola() {
        return caselleParola;
    }

    public void setCaselleParola(ArrayList<Casella> caselleParola) {
        this.caselleParola = caselleParola;
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

    public int getLettereInserite() {
        return lettereInserite;
    }

    public void setLettereInserite(int lettereInserite) {
        this.lettereInserite = lettereInserite;
    }

    //controlla se le due parole corrispondono alla stessa casella nello schema, sia come orientamento che come inizio e lunghezza parola
    public boolean confrontaCaselle(Parola p) {
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

    //aggiorna la stringa dentro parola con il testo delle caselle, contando anche le lettere inserite
    public boolean aggiornaParola(){
        StringBuilder strParolaNuova=new StringBuilder();
        boolean result=false;
        lettereInserite=0;
        try{
            if (lunghezza==caselleParola.size()){
                for (int i=0; i<lunghezza; i++){
                    char carattereCasella=caselleParola.get(i).getCarattereCasella();
                    if (carattereCasella!='.'){
                        lettereInserite++;
                        result=true;
                    }
                    strParolaNuova.append(carattereCasella);
                }
                parola=strParolaNuova.toString();
            }else{
                throw new Exception ("Lunghezza parola diversa dalla lunghezza della parola nel cruciverba");
            }
            return result;
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString(), "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //controlla se una parola è stata completata
    public boolean isComplete(){
        if (this.lunghezza==this.lettereInserite){
            return true;
        }else{
            return false;
        }
    }

    //controllo se esistono caselle uguali tra due parole
    public boolean isLinked(Parola p){
        for (Casella casellaParolaCorrente : caselleParola){
            for (Casella casellaParolaDaConfrontare : p.getCaselleParola()){
                if (casellaParolaCorrente.confrontaCaselle(casellaParolaDaConfrontare)){
                    return true;
                }
            }
        }
        return false;
    }

}