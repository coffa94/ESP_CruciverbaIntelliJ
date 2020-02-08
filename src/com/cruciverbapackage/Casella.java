package com.cruciverbapackage;


import javax.swing.*;
import java.awt.*;

public class Casella {
    private Posizione posizioneCasella;
    private JTextField textFieldCasella;
    private char carattereCasella;
    private boolean casellaNera;

    public Casella(JPanel panel, Posizione posizioneInput, char carattereInput, boolean casellaNeraInput) {
        int xIniziale = 20;
        int yIniziale = 50;
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
            textFieldCasella.setLocation(xIniziale + j * 20, yIniziale + i * 20);
            textFieldCasella.setText(String.valueOf(carattereCasella));
            panel.add(textFieldCasella);

            //imposto sfondo casella nera e la disabilito
            if (casellaNera) {
                textFieldCasella.setBackground(new Color(0, 0, 0));
                textFieldCasella.setEnabled(false);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean confrontaPosizione(int riga, int colonna) {
        Posizione posizioneDaConfrontare = new Posizione(riga, colonna);
        return posizioneCasella.equals(posizioneDaConfrontare);
    }
}
