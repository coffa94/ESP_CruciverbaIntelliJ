package com.cruciverbapackage;

//Coffaro_Davide_mat556603_Progetto ESP cruciverba

import javax.swing.*;
import java.util.ArrayList;

public class Schema {
    private ArrayList<Parola> paroleSchema;
    private ArrayList<Casella> caselleSchema;


    //costruttore che prende la matrice in ingresso e crea lo schema aggiungendo la prima parola
    public Schema(JPanel panel, char matrice[][], String parolaIniziale, Posizione posizioneParolaIniziale, char orientamentoInput) {
        //inizializzazione variabili classe
        paroleSchema = new ArrayList<Parola>();
        caselleSchema = new ArrayList<Casella>();
        ArrayList<Casella> caselleParola = new ArrayList<Casella>();
        Casella casellaAttuale;
        StringBuilder creazioneParola = new StringBuilder();
        Posizione posizioneIniziale = new Posizione();
        Posizione posizioneAttuale = new Posizione();
        Parola parolaCreata;
        int lunghezzaParola = 0;
        char carattere = '.';
        char casellaNera = '*';
        boolean primaLettera = true;

        //ricerca parole orizzontali
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                //inizializzazione posizione casella
                posizioneAttuale.setRiga(i);
                posizioneAttuale.setColonna(j);

                //ricerca della prima lettera della parola che verrà inserita nello schema
                if (matrice[i][j] == carattere && primaLettera) {
                    creazioneParola.append(' ');
                    primaLettera = false;
                    posizioneIniziale.setRiga(i);
                    posizioneIniziale.setColonna(j);
                    lunghezzaParola++;

                    //inizializzazione casella
                    casellaAttuale = new Casella(panel, posizioneAttuale, matrice[i][j], false);

                    caselleSchema.add(casellaAttuale);
                    caselleParola.add(casellaAttuale);
                } else if (matrice[i][j] == carattere && (!primaLettera)) { //ricerca lettere successive della parola che verrà inserita nello schema
                    creazioneParola.append(' ');
                    lunghezzaParola++;

                    //inizializzazione casella
                    casellaAttuale = new Casella(panel, posizioneAttuale, matrice[i][j], false);

                    caselleSchema.add(casellaAttuale);
                    caselleParola.add(casellaAttuale);
                } else if (matrice[i][j] == casellaNera) {  //ricerca delle caselle nere dello schema
                    if (lunghezzaParola >= 2) {  //lunghezza minima di una parola raggiunta, la inserisco nello schema così come la casella nera e ripristino i valori iniziali
                        caselleSchema.add(new Casella(panel, posizioneAttuale, matrice[i][j], true));
                        parolaCreata = new Parola(creazioneParola.toString(), posizioneIniziale, 'O', lunghezzaParola, caselleParola);
                        paroleSchema.add(parolaCreata);
                        creazioneParola = new StringBuilder();
                        posizioneIniziale.ripristinaPosizione();
                        lunghezzaParola = 0;
                        primaLettera = true;
                        caselleParola = new ArrayList<Casella>();
                    } else { // lunghezza minima di una parola non raggiunta, inserisco solo la casella nera e ripristino i valori iniziali
                        caselleSchema.add(new Casella(panel, posizioneAttuale, matrice[i][j], true));
                        creazioneParola = new StringBuilder();
                        posizioneIniziale.ripristinaPosizione();
                        lunghezzaParola = 0;
                        primaLettera = true;
                        caselleParola = new ArrayList<Casella>();
                    }

                }
            }
            if (lunghezzaParola >= 2) {  //raggiunta la fine della riga, inserisco la parola nello schema se ha la lunghezza minima altrimenti no
                parolaCreata = new Parola(creazioneParola.toString(), posizioneIniziale, 'O', lunghezzaParola, caselleParola);
                paroleSchema.add(parolaCreata);
                creazioneParola = new StringBuilder();
                posizioneIniziale.ripristinaPosizione();
                lunghezzaParola = 0;
                primaLettera = true;
                caselleParola = new ArrayList<Casella>();
            } else {
                creazioneParola = new StringBuilder();
                posizioneIniziale.ripristinaPosizione();
                lunghezzaParola = 0;
                primaLettera = true;
                caselleParola = new ArrayList<Casella>();
            }
        }

        //ricerca parole verticali, stessa procedura di quelle orizzontali con l'aggiunta del controllo per le caselle già presenti
        for (int j = 0; j < matrice[0].length; j++) {
            for (int i = 0; i < matrice.length; i++) {
                //ricerca della prima lettera della parola che verrà inserita nello schema
                if (matrice[i][j] == carattere && primaLettera) {
                    creazioneParola.append(' ');
                    primaLettera = false;
                    posizioneIniziale.setRiga(i);
                    posizioneIniziale.setColonna(j);
                    lunghezzaParola++;

                    //cerco la casella già esistente relativa alla riga i, colonna j per poi inserirla nella parola ed averla
                    // collegata alle caselle dello schema, così come alle parole in orizzontale
                    cercaCasella(caselleParola, i, j);

                } else if (matrice[i][j] == carattere && (!primaLettera)) {
                    creazioneParola.append(' ');
                    lunghezzaParola++;
                    cercaCasella(caselleParola, i, j);
                } else if (matrice[i][j] == casellaNera) {
                    if (lunghezzaParola >= 2) {
                        parolaCreata = new Parola(creazioneParola.toString(), posizioneIniziale, 'V', lunghezzaParola, caselleParola);
                        paroleSchema.add(parolaCreata);
                        creazioneParola = new StringBuilder();
                        posizioneIniziale.ripristinaPosizione();
                        lunghezzaParola = 0;
                        primaLettera = true;
                        caselleParola = new ArrayList<Casella>();
                    } else {
                        creazioneParola = new StringBuilder();
                        posizioneIniziale.ripristinaPosizione();
                        lunghezzaParola = 0;
                        primaLettera = true;
                        caselleParola = new ArrayList<Casella>();
                    }
                }
            }
            if (lunghezzaParola >= 2) {
                parolaCreata = new Parola(creazioneParola.toString(), posizioneIniziale, 'V', lunghezzaParola, caselleParola);
                paroleSchema.add(parolaCreata);
                creazioneParola = new StringBuilder();
                posizioneIniziale.ripristinaPosizione();
                lunghezzaParola = 0;
                primaLettera = true;
                caselleParola = new ArrayList<Casella>();
            } else {
                creazioneParola = new StringBuilder();
                posizioneIniziale.ripristinaPosizione();
                lunghezzaParola = 0;
                primaLettera = true;
                caselleParola = new ArrayList<Casella>();
            }
        }
        //aggiorna schema con la prima parola iniziale data in input
        aggiornaSchema(parolaIniziale, posizioneParolaIniziale, orientamentoInput);
    }

    //metodo costruttore schema a partire da uno schema già esistente
    public Schema(Schema s){
        this.paroleSchema=s.getParoleSchema();
        this.caselleSchema=s.getCaselleSchema();
    }

    public ArrayList<Parola> getParoleSchema() {
        return new ArrayList<Parola>(paroleSchema);
    }

    public ArrayList<Casella> getCaselleSchema(){ return new ArrayList<Casella>(caselleSchema); }

    // inserisce la parola in input nella parola dello schema con la stessa posizione e orientamento
    public void aggiornaSchema(String parola, Posizione posizioneParola, char orientamentoInput) {
        Parola p = new Parola(parola, posizioneParola, orientamentoInput, parola.length());
        for (Parola parolaSchema : paroleSchema) {
            if (parolaSchema.confrontaCaselle(p)) {
                parolaSchema.setParola(parola);
                parolaSchema.setLunghezza(parola.length());
                parolaSchema.aggiornaCaselleParola();
                //paroleSchema.set(paroleSchema.indexOf(parolaSchema), p);
                break;
            }
        }
        
        //dopo aver aggiornato una parola dello schema devo fare in modo di aggiornare le parole dello schema che hanno lettere collegate
        // alla parola appena aggiornata
        for(Parola parolaSchema : paroleSchema){
            parolaSchema.aggiornaParola();
        }

    }

    //cerca all'interno delle caselle dello schema e se già presente una casella con posizione riga, colonna allora la
    // assegna alle caselleParola senza creare una nuova casella apposita
    public void cercaCasella(ArrayList<Casella> caselleParola, int riga, int colonna) {
        for (Casella c : caselleSchema) {
            if (c.confrontaPosizione(riga, colonna)) {
                caselleParola.add(c);
            }
        }
    }

    //cerca le parole dello schema di lunghezza n non ancora completate
    public ArrayList<Parola> ricercaLunghezzaParole(int n){
        ArrayList<Parola> paroleLunghezzaN = new ArrayList<Parola>();
        for (Parola p : paroleSchema){
            int lunghezzaParola=p.getLunghezza();
            int lettereInserite=p.getLettereInserite();
            if (lunghezzaParola==n && lettereInserite<lunghezzaParola){
                paroleLunghezzaN.add(p);
            }
        }
        return paroleLunghezzaN;
    }

    //cerca la lunghezza massima tra le parole dello schema
    public int cercaLunghezzaParolaMax(){
        int lunghezzaMax=0;
        for (Parola p : paroleSchema){
            int lunghezzaCorrente=p.getLunghezza();
            if (lunghezzaCorrente > lunghezzaMax){
                lunghezzaMax=lunghezzaCorrente;
            }
        }
        return lunghezzaMax;
    }

}