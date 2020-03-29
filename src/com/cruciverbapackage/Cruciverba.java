package com.cruciverbapackage;

//Coffaro_Davide_mat556603_Progetto ESP cruciverba

import java.util.ArrayList;

public interface Cruciverba {
    //costruttore cruciverba con una struttura passata in input

    //@requires: this!=null, parola!=null, posizione all'interno dello schema
    //@modifies: this
    //@effects: inserisce la parola trovata all'interno del cruciverba
    //@throws: nullPointerException, positionException se posizione non interna al cruciverba
    //         lengthException se lunghezza parola non entra nel cruciverba
    public void aggiornaParola(String parola,int riga, int colonna, char orientamento);

    //@requires: this!=null, lettera!=null, posizione all'interno del cruciverba
    //@modifies: this
    //@effects: inserisce la lettera dentro il cruciverba alla posizione indicata
    //@throws: nullPointerException, positionException se posizione non interna al cruciverba
    public void aggiornaLettera(char lettera, int riga, int colonna);

    //@requires: this!=null
    //@effects: visualizza lo schema del cruciverba a video
    //@throws: nullPointerException
    //@return: componenteDaVisualizzare T (di tipo grafico??)
    //public <T> T visualizzaSchema();

    //@requires: this!=null
    //@effects: ricerca la prossima parola da inserire nel cruciverba
    //@throws: nullPointerException
    //@return: parola trovata E oppure null (in questo caso va gestito)
    public String cercaParolaDaInserire(Parola casellaDaCompletare, ArrayList<String> dizionario);

    //@requires: this!=null
    //@effects: chiama cercaParolaDaInserire finche lo schema non è completato, cioè isComplete=true
    //@throws: nullPointerException
    //@return: true se completato, false se non è possibile completarlo
    public boolean risolviCruciverba();

    //@requires: this!=null
    //@effects: controllo se cruciverba è finito o no
    //@throws: nullPointerException
    //@return: true se completo, false altrimenti
    public boolean isComplete();

}