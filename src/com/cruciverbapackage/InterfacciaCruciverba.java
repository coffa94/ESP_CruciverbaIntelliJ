package com.cruciverbapackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;

public class InterfacciaCruciverba {
    private JPanel panelMain;
    private JLabel labelCruciverba;
    private JButton buttonRisolviCruciverba;
    private JButton buttonCercaParola;
    private static ImplementazioneCruciverba cruciverba1;
    private static char[][] matrice = {{'.', '.', '.', '.', '*', '.', '.', '.'}, {'*', '.', '.', '.', '.', '*', '.', '.'}, {'.', '.', '.', '.', '.', '.', '*', '.'}, {'.', '.', '*', '.', '.', '.', '.', '.',}};
    private ArrayList<JTextField> text;
    private static ArrayList<String> dizionarioInput;

    public InterfacciaCruciverba() {
        createUIComponents();
        buttonRisolviCruciverba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //risoluzione tramite algoritmo1
                // if(cruciverba1.risolviCruciverba_alg1()){
                //  JOptionPane.showMessageDialog(null, "Cruciverba completato", "Risultato cruciverba", JOptionPane.INFORMATION_MESSAGE);
                // }else{
                //  JOptionPane.showMessageDialog(null, "Cruciverba non completato", "Risultato cruciverba", JOptionPane.ERROR_MESSAGE);
                // }

                //risoluzione tramite algoritmo2
                if (cruciverba1.risolviCruciverba_alg2()) {
                    JOptionPane.showMessageDialog(null, "Cruciverba completato", "Risultato cruciverba", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Cruciverba non completato", "Risultato cruciverba", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
        buttonCercaParola.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cruciverba");
        InterfacciaCruciverba window = new InterfacciaCruciverba();

        frame.setContentPane(window.panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(480, 200);
        frame.setVisible(true);

        dizionarioInput = new ArrayList<String>();
        dizionarioInput.add("CAT");
        dizionarioInput.add("NOIA");
        dizionarioInput.add("ANCA");
        dizionarioInput.add("NOE");
        dizionarioInput.add("EIRE");
        dizionarioInput.add("AR");
        dizionarioInput.add("TOPO");
        dizionarioInput.add("RO");
        dizionarioInput.add("ABS");
        dizionarioInput.add("ACERBO");
        dizionarioInput.add("AP");
        dizionarioInput.add("OI");
        dizionarioInput.add("PA");
        dizionarioInput.add("ESITO");


        //window.createUIComponents();
        window.open();
        frame.setContentPane(window.panelMain);

    }

    public void open() {
        //creaCruciverba(matrice);
        cruciverba1 = new ImplementazioneCruciverba(panelMain, matrice, "CANE", 0, 0, dizionarioInput);


        //aggiornaCruciverba(cruciverba1.VisualizzaSchema());

    }

    protected void creaCruciverba(char[][] matrice) {
        int xIniziale = 20;
        int yIniziale = 50;
        int index = 0;
        text = new ArrayList<JTextField>();

        /*for (int i=0; i<matrice.length; i++) {
            for (int j=0; j<matrice[0].length; j++) {
                if (matrice[i][j]=='.'){
                    text.add( new JTextField());
                    index++;
                    text.get(index-1).setSize( 20, 20);
                    text.get(index-1).setLocation(xIniziale+j*20, yIniziale+i*20);
                    panelMain.add(text.get(index-1));
                    panelMain.revalidate();
                }else if(matrice[i][j]=='*') {
                    JTextField t=new JTextField();
                    t.setBackground(new Color( 0, 0, 0));
                    t.setEnabled(false);
                    text.add(t);
                    index++;
                    text.get(index-1).setSize(20, 20);
                    text.get(index-1).setLocation(xIniziale+j*20, yIniziale+i*20);
                    panelMain.add(text.get(index-1));
                    panelMain.revalidate();
                }
            }
        }*/

        //text = new Text(composite, SWT.BORDER);
        //text.setBounds(125, 66, 21, 21);
    }

    protected void aggiornaCruciverba(char[][] matriceCruciverba) {

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        panelMain = new JPanel();
        GroupLayout layoutGUI = new GroupLayout(panelMain);
        panelMain.setLayout(layoutGUI);
        layoutGUI.setAutoCreateGaps(true);
        layoutGUI.setAutoCreateContainerGaps(true);

        labelCruciverba = new JLabel("Cruciverba 4X8");
        labelCruciverba.setBounds(20, 20, 400, 20);

        buttonRisolviCruciverba = new JButton("RisolviCruciverba");
        buttonRisolviCruciverba.setBounds(20, 140, 200, 20);
        buttonCercaParola = new JButton("CercaParola");
        buttonCercaParola.setBounds(240, 140, 200, 20);

        panelMain.add(labelCruciverba);
        panelMain.add(buttonRisolviCruciverba);
        panelMain.add(buttonCercaParola);
        panelMain.revalidate();


        //panelMain.setLayout(ccc);
//panelMain.setLayout(null);
        //panelCruciverba.setLayout(ccc);
        //System.out.println(panelCruciverba.getLayout().toString());
        //panelCruciverba.setSize(100,100);
        //panelMain.add(panelCruciverba);
        /*JTextField comp = new JTextField("ciao");
        comp.setText("Ciao");
        comp.setSize(100, 100);
        //panelCruciverba.add(comp);
        System.out.print(panelMain.getLayout().toString());
        panelMain.add(comp);
        panelMain.revalidate();*/
        //System.out.print(panelMain.getLayout().toString());

    }
}
