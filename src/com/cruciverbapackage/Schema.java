package com.cruciverbapackage;

import java.util.ArrayList;

public class Schema {
    ArrayList<Parola> paroleSchema;
    StringBuilder creazioneParola;
    Parola parolaCreata;
    ArrayList<Posizione> caselleNere;
    int lunghezzaParola=0;
    char carattere='.';
    char casellaNera='*';
    boolean primaLettera=true;
    Posizione posizioneIniziale;

    //Metodo costruttore
    //@modifies: this
    //@effects: prende la matrice in ingresso e crea lo schema originale
    public Schema(char matrice[][], String parolaIniziale, Posizione posizioneParolaIniziale, char orientamentoInput) {
        //inizializzazione variabili classe
        paroleSchema=new ArrayList<Parola>();
        caselleNere = new ArrayList<Posizione>();
        creazioneParola=new StringBuilder();
        posizioneIniziale=new Posizione();

        //ricerca parole orizzontali
        for (int i=0; i<matrice.length; i++) {
            for (int j=0; j<matrice[0].length; j++) {
                //ricerca della prima lettera della parola che verrà inserita nello schema
                if (matrice[i][j]==carattere && primaLettera){
                    creazioneParola.append(' ');
                    primaLettera=false;
                    posizioneIniziale.setRiga(i);
                    posizioneIniziale.setColonna(j);
                    lunghezzaParola++;
                }else if(matrice[i][j]==carattere && (!primaLettera)) {
                    creazioneParola.append(' ');
                    lunghezzaParola++;
                }else if(matrice[i][j]==casellaNera) {
                    if (lunghezzaParola>=2) {
                        parolaCreata=new Parola(creazioneParola.toString(), posizioneIniziale, 'O', lunghezzaParola);
                        paroleSchema.add(parolaCreata);
                        creazioneParola=new StringBuilder();
                        posizioneIniziale.ripristinaPosizione();
                        lunghezzaParola=0;
                        primaLettera=true;
                    }else {
                        creazioneParola=new StringBuilder();
                        posizioneIniziale.ripristinaPosizione();
                        lunghezzaParola=0;
                        primaLettera=true;
                    }
                    caselleNere.add(new Posizione(i,j));
                }
            }
            if (lunghezzaParola>=2){
                parolaCreata=new Parola(creazioneParola.toString(), posizioneIniziale, 'O', lunghezzaParola);
                paroleSchema.add(parolaCreata);
                creazioneParola=new StringBuilder();
                posizioneIniziale.ripristinaPosizione();
                lunghezzaParola=0;
                primaLettera=true;
            }else {
                creazioneParola=new StringBuilder();
                posizioneIniziale.ripristinaPosizione();
                lunghezzaParola=0;
                primaLettera=true;
            }
        }

        //ricerca parole verticali
        for (int j=0; j<matrice[0].length; j++) {
            for (int i=0; i<matrice.length; i++) {
                //ricerca della prima lettera della parola che verrà inserita nello schema
                if (matrice[i][j]==carattere && primaLettera){
                    creazioneParola.append(' ');
                    primaLettera=false;
                    posizioneIniziale.setRiga(i);
                    posizioneIniziale.setColonna(j);
                    lunghezzaParola++;
                }else if(matrice[i][j]==carattere && (!primaLettera)) {
                    creazioneParola.append(' ');
                    lunghezzaParola++;
                }else if(matrice[i][j]==casellaNera) {
                    if (lunghezzaParola>=2) {
                        parolaCreata=new Parola(creazioneParola.toString(), posizioneIniziale, 'V', lunghezzaParola);
                        paroleSchema.add(parolaCreata);
                        creazioneParola=new StringBuilder();
                        posizioneIniziale.ripristinaPosizione();
                        lunghezzaParola=0;
                        primaLettera=true;
                    }else {
                        creazioneParola=new StringBuilder();
                        posizioneIniziale.ripristinaPosizione();
                        lunghezzaParola=0;
                        primaLettera=true;
                    }
                }
            }
            if (lunghezzaParola>=2){
                parolaCreata=new Parola(creazioneParola.toString(), posizioneIniziale, 'V', lunghezzaParola);
                paroleSchema.add(parolaCreata);
                creazioneParola=new StringBuilder();
                posizioneIniziale.ripristinaPosizione();
                lunghezzaParola=0;
                primaLettera=true;
            }else {
                creazioneParola=new StringBuilder();
                posizioneIniziale.ripristinaPosizione();
                lunghezzaParola=0;
                primaLettera=true;
            }
        }
        //aggiorna schema con la prima parola iniziale data in input
        AggiornaSchema(parolaIniziale, posizioneParolaIniziale, orientamentoInput);
    }

    //@requires: this!=null, parola entra nello schema (lunghezza corretta), posizione esistente
    //@modifies: this
    //@effects: inserisce la parola nella posizione iniziale indicata e poi dopo questa funzione
    //deve essere richiamato anche aggiornaSchemaScomposto
    //@throws: nullPointerException, wordException se la parola da inserire è troppo lunga,
    //positionException se la posizione non entra nello schema
    public void AggiornaSchema(String parola, Posizione posizioneParola, char orientamentoInput) {
        Parola p = new Parola(parola, posizioneParola, orientamentoInput, parola.length());
        for (Parola parolaSchema : paroleSchema){
            if (parolaSchema.ConfrontaCaselle(p)){
                paroleSchema.set(paroleSchema.indexOf(parolaSchema), p);
                break;
            }
        }
    }

    public char[][] SchemaToMatrice() {
        char[][] matrice={{1,2},{3,4}};
        return matrice;
    }



}