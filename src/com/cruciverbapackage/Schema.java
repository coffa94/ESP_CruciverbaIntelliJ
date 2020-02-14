package com.cruciverbapackage;

import javax.swing.*;
import java.util.ArrayList;

public class Schema {
    private ArrayList<Parola> paroleSchema;
    private ArrayList<Casella> caselleSchema;


    //Metodo costruttore
    //@modifies: this
    //@effects: prende la matrice in ingresso e crea lo schema originale
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
                } else if (matrice[i][j] == carattere && (!primaLettera)) {
                    creazioneParola.append(' ');
                    lunghezzaParola++;

                    //inizializzazione casella
                    casellaAttuale = new Casella(panel, posizioneAttuale, matrice[i][j], false);

                    caselleSchema.add(casellaAttuale);
                    caselleParola.add(casellaAttuale);
                } else if (matrice[i][j] == casellaNera) {
                    if (lunghezzaParola >= 2) {
                        caselleSchema.add(new Casella(panel, posizioneAttuale, matrice[i][j], true));
                        parolaCreata = new Parola(creazioneParola.toString(), posizioneIniziale, 'O', lunghezzaParola, caselleParola);
                        paroleSchema.add(parolaCreata);
                        creazioneParola = new StringBuilder();
                        posizioneIniziale.ripristinaPosizione();
                        lunghezzaParola = 0;
                        primaLettera = true;
                        caselleParola = new ArrayList<Casella>();
                    } else {
                        caselleSchema.add(new Casella(panel, posizioneAttuale, matrice[i][j], true));
                        creazioneParola = new StringBuilder();
                        posizioneIniziale.ripristinaPosizione();
                        lunghezzaParola = 0;
                        primaLettera = true;
                        caselleParola = new ArrayList<Casella>();
                    }

                }
            }
            if (lunghezzaParola >= 2) {
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

        //ricerca parole verticali
        for (int j = 0; j < matrice[0].length; j++) {
            for (int i = 0; i < matrice.length; i++) {
                //ricerca della prima lettera della parola che verrà inserita nello schema
                if (matrice[i][j] == carattere && primaLettera) {
                    creazioneParola.append(' ');
                    primaLettera = false;
                    posizioneIniziale.setRiga(i);
                    posizioneIniziale.setColonna(j);
                    lunghezzaParola++;

                    //cerco la casella già esistente relativa alla riga i, colonna j per poi inserirla nella parola ed averla collegata alle caselle dello schema, così come alle parole in orizzontale
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

    public ArrayList<Parola> getParoleSchema() {
        return paroleSchema;
    }

    //@requires: this!=null, parola entra nello schema (lunghezza corretta), posizione esistente
    //@modifies: this
    //@effects: inserisce la parola nella posizione iniziale indicata e poi dopo questa funzione
    //deve essere richiamato anche aggiornaSchemaScomposto
    //@throws: nullPointerException, wordException se la parola da inserire è troppo lunga,
    //positionException se la posizione non entra nello schema
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

    public char[][] schemaToMatrice() {
        char[][] matrice = {{1, 2}, {3, 4}};
        return matrice;
    }

    public void cercaCasella(ArrayList<Casella> caselleParola, int riga, int colonna) {
        for (Casella c : caselleSchema) {
            if (c.confrontaPosizione(riga, colonna)) {
                caselleParola.add(c);
            }
        }
    }

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


}