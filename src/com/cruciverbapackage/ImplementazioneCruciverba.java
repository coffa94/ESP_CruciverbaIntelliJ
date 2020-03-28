package com.cruciverbapackage;

import javax.imageio.plugins.tiff.ExifTIFFTagSet;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImplementazioneCruciverba implements Cruciverba {
    protected Schema schema_originale;
    //protected SchemaScomposto schema_scomposto;
    protected ArrayList<String> dizionario;

    //costruttore cruciverba con una struttura passata in input
    public ImplementazioneCruciverba(JPanel panel, char matrice[][], String parolaIniziale, int posizioneRigaIniziale, int posizioneColonnaIniziale, ArrayList<String> dizionarioInput) {
        schema_originale = new Schema(panel, matrice, parolaIniziale, new Posizione(posizioneRigaIniziale, posizioneColonnaIniziale), 'O');
        if (dizionarioInput != null && dizionarioInput.size() != 0) {
            dizionario = dizionarioInput;
        }

    }

    //@requires: this!=null, parola!=null, posizione all'interno dello schema
    //@modifies: this
    //@effects: inserisce la parola trovata all'interno del cruciverba
    //@throws: nullPointerException, positionException se posizione non interna al cruciverba
    //         lengthException se lunghezza parola non entra nel cruciverba
    public void aggiornaParola(String parola, int riga, int colonna, char orientamento) {
        schema_originale.aggiornaSchema(parola,new Posizione(riga,colonna),orientamento);
    }

    //@requires: this!=null, lettera!=null, posizione all'interno del cruciverba
    //@modifies: this
    //@effects: inserisce la lettera dentro il cruciverba alla posizione indicata
    //@throws: nullPointerException, positionException se posizione non interna al cruciverba
    public void aggiornaLettera(char lettera, int riga, int colonna) {

    }

    //@requires: this!=null
    //@effects: ricerca la prossima parola da inserire nel cruciverba
    //@throws: nullPointerException
    //@return: parola trovata E oppure null (in questo caso va gestito)
    public String cercaParolaDaInserire(Parola casellaDaCompletare, ArrayList<String> dizionario){
        return "";
    }

    //TODO funzione da lanciare al click su cercaParola
    //corrisponde ad un ciclo di risolviCruciverba (in cui poi viene lanciata la funzione cercaParolaDaInserire


    //@requires: this!=null
    //@effects: chiama n volte cercaParolaDaInserire finche lo schema non è completato, cioè isComplete=true
    //@throws: nullPointerException
    //@return: true se completato, false se non è possibile completarlo
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

    //@requires: this!=null
    //@effects: controllo se cruciverba è finito o no
    //@throws: nullPointerException
    //@return: true se completo, false altrimenti
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