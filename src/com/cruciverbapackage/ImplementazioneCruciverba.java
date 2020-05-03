package com.cruciverbapackage;

//Coffaro_Davide_mat556603_Progetto ESP cruciverba

import javax.imageio.plugins.tiff.ExifTIFFTagSet;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImplementazioneCruciverba {
    protected Schema schema_originale;
    protected ArrayList<String> dizionario;
    protected boolean algResult;
    protected boolean algExecuted;

    //costruttore cruciverba con una struttura passata in input
    public ImplementazioneCruciverba(JPanel panel, char matrice[][], String parolaIniziale, int posizioneRigaIniziale, int posizioneColonnaIniziale, ArrayList<String> dizionarioInput, char orientamento) {
        schema_originale = new Schema(panel, matrice, parolaIniziale, new Posizione(posizioneRigaIniziale, posizioneColonnaIniziale), orientamento);
        if (dizionarioInput != null && dizionarioInput.size() != 0) {
            dizionario = dizionarioInput;
        }

    }

    public ArrayList<String> getDizionario() {
        return dizionario;
    }

    public boolean isAlgResult() {
        return algResult;
    }

    public boolean isAlgExecuted() {
        return algExecuted;
    }

    // inserisce la parola all'interno del cruciverba nella riga e nella colonna specificata e con quell'orientamento
    public void aggiornaParola(String parola, int riga, int colonna, char orientamento) {
        schema_originale.aggiornaSchema(parola,new Posizione(riga,colonna),orientamento);
    }

    // ricerca la prossima parola da inserire nel cruciverba
    public String cercaParolaDaInserire(Parola casellaDaCompletare, ArrayList<String> dizionario){
        return "";
    }

    //corrisponde ad un ciclo di risolviCruciverba (in cui poi viene lanciata la funzione cercaParolaDaInserire)
    //inserisce una parola nello schema del cruciverba
    public String inserisci1Parola(){ return null; }

    //chiama cercaParolaDaInserire finche lo schema non è completato, cioè isComplete=true
    public boolean risolviCruciverba(){
        return true;
    }

    //cerco la parola all'interno della lista che ha più lettere già inserite, a parità di lettere già inserite prendo la prima che ho trovato
    public Parola cercaParolaConPiuLettere(ArrayList<Parola> listaParole){
        int maxLettereInserite=-1, contatoreLettereInserite=0;
        Parola maxParolaLettereInserite=null;
        for (Parola p : listaParole){
            contatoreLettereInserite=p.getLettereInserite();
            if (contatoreLettereInserite>maxLettereInserite){
                maxLettereInserite=contatoreLettereInserite;
                maxParolaLettereInserite=p;
            }
        }
        return maxParolaLettereInserite;
    }

    // controllo se cruciverba è finito o no, quindi non risultano altre parole del dizionario da inserire
    public boolean isComplete() {
        if (dizionario.size()==0){
            return true;
        }else{
            return false;
        }
    }
    
    //aggiornamento dizionario con le parole dello schema COMPLETATE, in questo modo quelle che parole che si sono completate automaticamente inserendo
    //altre parole nello schema vengono eliminate dal dizionario
    public void aggiornaDizionario(){
        ArrayList<Parola> paroleSchema = schema_originale.getParoleSchema();
        String parolaCorrente;
        for (Parola p : paroleSchema){
            parolaCorrente=p.getParola();
            if (p.getLunghezza()==p.getLettereInserite() && dizionario.contains(parolaCorrente)){
                dizionario.remove(parolaCorrente);
            }
        }
    }

}