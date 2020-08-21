package com.cruciverbapackage;

//Coffaro_Davide_mat556603_Progetto ESP cruciverba

import org.junit.jupiter.api.parallel.Resources;
import org.junit.rules.Stopwatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.lang.Object;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfacciaCruciverba {
    private JPanel panelMain;
    private JPanel panelListaParole;
    private JLabel labelCruciverba;
    private JButton buttonRisolviCruciverba;
    private JButton buttonCercaParola;
    private JList listListaParole;
    private JScrollPane scrollPaneListaParole;
    private JLabel labelParolaInserita;
    private JTextField textFieldParolaInserita;
    private static ImplementazioneCruciverba cruciverba1;
    private static int dimensioneFinestraMinima=500;
    private long startTime=0;
    private long stopTime=0;
    private long totalTime=0;

    //inizializzazione della matrici che faranno da base dello schema del cruciverba per i 3 esempi creati
    //proposto un miglioramento a questa implementazione all'interno della relazione (nella sezione Scelte implementative)
    //esempio1
    private static char[][] matrice = {{'.', '.', '.', '.', '*', '.', '.', '.'},
                                       {'*', '.', '.', '.', '.', '*', '.', '.'},
                                       {'.', '.', '.', '.', '.', '.', '*', '.'},
                                       {'.', '.', '*', '.', '.', '.', '.', '.'}};


    /*//esempio2
    private static char[][] matrice = {{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '*', '.'},
                                       {'.', '*', '.', '.', '.', '.', '.', '*', '.', '*', '.', '.'},
                                       {'.', '.', '.', '.', '.', '.', '*', '.', '.', '.', '.', '.'},
                                       {'.', '.', '.', '.', '.', '*', '.', '.', '.', '.', '.', '.'},
                                       {'.', '.', '.', '.', '*', '.', '*', '.', '.', '.', '.', '.'},
                                       {'.', '.', '.', '*', '.', '.', '.', '.', '.', '.', '.', '*'},
                                       {'.', '.', '*', '.', '.', '.', '.', '.', '.', '.', '*', '.'},
                                       {'.', '*', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
                                       {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '*', '.'},
                                       {'.', '*', '.', '.', '.', '.', '.', '.', '.', '*', '.', '.'},
                                       {'*', '.', '.', '.', '.', '.', '.', '.', '*', '.', '.', '.'},
                                       {'.', '.', '.', '.', '.', '*', '.', '*', '.', '.', '.', '.'},
                                       {'.', '.', '.', '.', '.', '.', '*', '.', '.', '.', '.', '.'},
                                       {'.', '.', '.', '.', '.', '*', '.', '.', '.', '.', '.', '.'},
                                       {'.', '.', '*', '.', '*', '.', '.', '.', '.', '.', '*', '.'},
                                       {'.', '*', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'}};
*/

    //esempio3
/*    private static char[][] matrice = {{'.','.','*','.','.','.','.','.','.','.','.','.','*','.','.','.','.','.','.'},
                                       {'.','.','.','.','.','.','.','.','.','.','.','.','.','*','.','.','.','.','*'},
                                       {'.','.','.','*','.','*','.','.','.','*','.','*','.','.','*','.','.','.','.'},
                                       {'.','*','.','.','*','.','*','.','*','.','*','.','.','.','*','.','.','.','.'},
                                       {'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','*','.','.'},
                                       {'.','.','.','.','.','.','.','.','.','.','.','*','.','.','.','.','.','.','.'},
                                       {'.','.','.','.','.','.','.','.','.','.','.','.','*','.','.','.','.','.','*'},
                                       {'.','.','.','.','.','.','.','.','.','*','.','.','.','.','.','*','.','.','.'},
                                       {'.','.','*','.','*','.','.','.','.','.','.','.','.','*','.','.','.','.','.'},
                                       {'.','.','.','.','.','.','.','*','.','.','.','.','.','.','*','.','.','.','.'},
                                       {'.','*','.','.','.','.','*','.','.','.','.','.','.','*','.','.','.','.','.'},
                                       {'*','.','.','.','.','*','.','.','.','*','.','.','*','.','.','.','.','.','*'}};
*/

    private ArrayList<JTextField> text;
    private static ArrayList<String> dizionarioInput;
    private static String parolaIniziale;
    private static int posizioneRigaIniziale;
    private static int posizioneColonnaIniziale;
    private static char orientamento;

    public InterfacciaCruciverba() {
        createUIComponents();
        buttonRisolviCruciverba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTime=System.currentTimeMillis();

                //lancia procedura di risoluzione cruciverba e ritornando se è stata trovata la soluzione o meno, stampando un messaggio a video
                if (cruciverba1.risolviCruciverba()) {
                    listListaParole.setListData(cruciverba1.dizionario.toArray());
                    stopTime=System.currentTimeMillis();
                    totalTime=stopTime-startTime;
                    JOptionPane.showMessageDialog(null, "Cruciverba completato in " + totalTime, "Risultato cruciverba", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    listListaParole.setListData(cruciverba1.dizionario.toArray());
                    JOptionPane.showMessageDialog(null, "Cruciverba non completato", "Risultato cruciverba", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        buttonCercaParola.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //lancia procedura inserisci1Parola per cui esegue un passo dell'algoritmo e inserisce la parola trovata, se non trova nessuna
                // parola controlla il risultato del cruciverba
                String parolaInserita=cruciverba1.inserisci1Parola();
                if (parolaInserita==null) {
                    listListaParole.setListData(cruciverba1.dizionario.toArray());
                    stopTime=System.currentTimeMillis();
                    totalTime=stopTime-startTime;
                    if (cruciverba1.isAlgResult()) {
                        JOptionPane.showMessageDialog(null, "Cruciverba completato in " + totalTime, "Risultato cruciverba", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, "Cruciverba non completato", "Risultato cruciverba", JOptionPane.ERROR_MESSAGE);

                    }
                } else {
                    textFieldParolaInserita.setText(parolaInserita);
                    listListaParole.setListData(cruciverba1.dizionario.toArray());
                    //JOptionPane.showMessageDialog(null, "Cruciverba non completato", "Risultato cruciverba", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cruciverba");
        InterfacciaCruciverba window = new InterfacciaCruciverba();

        frame.setContentPane(window.panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        //imposto la dimensione della finestra a seconda del numero di righe e di colonne del cruciverba, se sono troppo basse imposto una
        //dimensione minima
        int dimensioneFinestra=20*(matrice.length+matrice[0].length)+100;
        if (dimensioneFinestra<dimensioneFinestraMinima){
            frame.setSize(dimensioneFinestraMinima, dimensioneFinestraMinima);
        }else{
            frame.setSize(dimensioneFinestra, dimensioneFinestra);
        }
        frame.setVisible(true);

        try {
            dizionarioInput = new ArrayList<String>();

            //importo lista di parole che potranno essere inserite nello schema del cruciverba per i 3 esempi creati
            //la prima riga del file .txt contiene la prima parola da inserire nello schema, con i riferimenti riga,
            //colonna e orientamento.
            //Come descritto nella sezione Scelte implementative della relazione, questa implementazione sarebbe
            //migliorabile creando una procedura di selezione di un file .txt

            //esempio1
            //File f = new File("./esempio1.txt");

            //esempio2
            //File f = new File("./esempio2.txt");

            //esempio3
            //File f = new File("./esempio3.txt");

            //esempio4 - dizionario italiano completo
            File f = new File("./paroleitaliane.txt");

            if (f.isFile()) {
                Scanner s = new Scanner(f);
                if (s == null) {
                    System.out.println("Scanner non creato");
                }else {
                    //imposto il delimitatore per poter analizzare la prima riga contenente la parola da inserire per prima nello schema
                    // con la sua posizione e il suo orientamento
                    //s.useDelimiter(",");
                    if(s.hasNext()){
                        parolaIniziale = s.next();
                        if(s.hasNextInt()){
                            posizioneRigaIniziale = Integer.valueOf(s.next());
                            if(s.hasNextInt()){
                                posizioneColonnaIniziale = Integer.valueOf(s.next());
                                if(s.hasNext()){
                                    orientamento = s.next().charAt(0);

                                    s.reset();
                                    //inserimento delle parole nella lista di parole disponibili
                                    while (s.hasNextLine()) {
                                        String riga = s.nextLine();
                                        dizionarioInput.add(riga);
                                    }
                                }else{
                                    System.out.println("Nessuna orientamento inserito");
                                    throw new InputMismatchException();
                                }
                            }else{
                                System.out.println("Nessuna posizione colonna iniziale inserita");
                                throw new InputMismatchException();
                            }
                        }else{
                            System.out.println("Nessuna posizione riga iniziale inserita");
                            throw new InputMismatchException();
                        }
                    }else{
                        System.out.println("Nessuna parola iniziale inserita");
                        throw new InputMismatchException();
                    }

                    s.close();
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found exception");
            System.exit(-1);
        }catch(Exception e){
            System.exit(-1);
        }


        //window.createUIComponents();
        window.open();
        frame.setContentPane(window.panelMain);

    }

    public void open() {

        //creazione cruciverba per l'utilizzo di funzioni dell'algoritmo1
        cruciverba1 = new ImplAlg1Cruciverba(panelMain, matrice, parolaIniziale, posizioneRigaIniziale, posizioneColonnaIniziale, dizionarioInput,orientamento);

        //creazione cruciverba per l'utilizzo di funzioni dell'algoritmo2
        //cruciverba1=new ImplAlg2Cruciverba(panelMain, matrice, parolaIniziale, posizioneRigaIniziale, posizioneColonnaIniziale, dizionarioInput, orientamento);

        //creazione cruciverba per l'utilizzo di funzioni dell'algoritmo4
        //cruciverba1 = new ImplAlg4Cruciverba_AI(panelMain,matrice, parolaIniziale, posizioneRigaIniziale, posizioneColonnaIniziale, dizionarioInput,orientamento);

        listListaParole.setListData(dizionarioInput.toArray());

    }


    private void createUIComponents() {
        // creo tutti gli elementi grafici dell'interfaccia utente per il cruciverba escluse tutte le caselle bianche e nere che creo in futuro
        panelMain = new JPanel();
        GroupLayout layoutGUI = new GroupLayout(panelMain);
        panelMain.setLayout(layoutGUI);
        layoutGUI.setAutoCreateGaps(true);
        layoutGUI.setAutoCreateContainerGaps(true);

        labelCruciverba = new JLabel("Cruciverba " + matrice.length + "X" + matrice[0].length);
        labelCruciverba.setBounds(20, 20, 400, 20);

        buttonRisolviCruciverba = new JButton("RisolviCruciverba");
        buttonRisolviCruciverba.setBounds(20, 40, 200, 20);
        buttonCercaParola = new JButton("CercaParola");
        buttonCercaParola.setBounds(240, 40, 200, 20);

        listListaParole=new JList();
        listListaParole.setBounds(20, 80, 200,200);

        scrollPaneListaParole = new JScrollPane(listListaParole);
        scrollPaneListaParole.setPreferredSize(new Dimension(300,200));
        scrollPaneListaParole.setBounds(20,80,800,800);

        panelListaParole = new JPanel();
        BorderLayout groupLayoutListaParole = new BorderLayout();
        panelListaParole.setLayout(groupLayoutListaParole);
        panelListaParole.add(scrollPaneListaParole);
        panelListaParole.setBounds(20,80,200,200);

        labelParolaInserita = new JLabel("Ultima parola inserita");
        labelParolaInserita.setBounds(20, 300, 400, 20);

        textFieldParolaInserita=new JTextField();
        textFieldParolaInserita.setSize(200, 20);
        textFieldParolaInserita.setLocation(20,  320);


        panelMain.add(labelCruciverba);
        panelMain.add(buttonRisolviCruciverba);
        panelMain.add(buttonCercaParola);
        panelMain.add(panelListaParole);
        panelMain.add(labelParolaInserita);
        panelMain.add(textFieldParolaInserita);
        panelMain.revalidate();

    }
}
