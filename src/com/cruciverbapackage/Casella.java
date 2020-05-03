package com.cruciverbapackage;

//Coffaro_Davide_mat556603_Progetto ESP cruciverba

import javax.swing.*;
import java.awt.*;

public class Casella {
    private Posizione posizioneCasella;
    private JTextField textFieldCasella;
    private char carattereCasella;
    private boolean casellaNera;

    //costruttore casella con input il Jpanel dove verrà inserita la casella
    public Casella(JPanel panel, Posizione posizioneInput, char carattereInput, boolean casellaNeraInput) {
        int xIniziale = 240;
        int yIniziale = 80;
        int i, j = 0;
        try {
            if (posizioneInput != null) {
                posizioneCasella = new Posizione(posizioneInput);
                i = posizioneCasella.getRiga();
                j = posizioneCasella.getColonna();
            } else {
                throw new Exception("Posizione non esistente");
            }
            carattereCasella = carattereInput;
            casellaNera = casellaNeraInput;
            textFieldCasella = new JTextField();
            textFieldCasella.setSize(20, 20);
            //calcolo la posizione della casella a partire dalla posizione passata in input
            textFieldCasella.setLocation(xIniziale + j * 20, yIniziale + i * 20);
            textFieldCasella.setText(String.valueOf(carattereCasella));


            //imposto lo sfondo della casella nera e la disabilito
            if (casellaNera) {
                textFieldCasella.setBackground(new Color(0, 0, 0));
                textFieldCasella.setEditable(false);
                textFieldCasella.setFocusable(false);
            }

            //aggancio il textField al pannello passato in input
            panel.add(textFieldCasella);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public char getCarattereCasella() {
        return carattereCasella;
    }

    public void setCarattereCasella(char carattereCasella) {
        this.carattereCasella = carattereCasella;
    }

    //controlla ritornando true o false se la posizione della casella dell'oggetto corrente è uguale a quella passata in input
    public boolean confrontaPosizione(int riga, int colonna) {
        Posizione posizioneDaConfrontare = new Posizione(riga, colonna);
        return posizioneCasella.equals(posizioneDaConfrontare);
    }

    //procedura di confronto caselle, ritorna true se la casella in input è la stessa della casella dell'oggetto corrente
    public boolean confrontaCaselle(Casella c){
        return posizioneCasella.equals(c.posizioneCasella);
    }

    //aggiorna il carattere dell'oggetto corrente e il testo del componente visualizzato a video
    public void aggiornaCarattere(char carattereInput){
        carattereCasella=carattereInput;
        textFieldCasella.setText(String.valueOf(carattereInput));
    }
}
