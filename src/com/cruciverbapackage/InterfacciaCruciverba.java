package com.cruciverbapackage;

//Coffaro_Davide_mat556603_Progetto ESP cruciverba

import org.junit.rules.Stopwatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.lang.Object;

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

    /*//esempio1
    private static char[][] matrice = {{'.', '.', '.', '.', '*', '.', '.', '.'},
                                       {'*', '.', '.', '.', '.', '*', '.', '.'},
                                       {'.', '.', '.', '.', '.', '.', '*', '.'},
                                       {'.', '.', '*', '.', '.', '.', '.', '.'}};
*/

    //esempio2
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

    /*//esempio3
    private static char[][] matrice = {{'.','.','*','.','.','.','.','.','.','.','.','.','*','.','.','.','.','.','.'},
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

    public InterfacciaCruciverba() {
        createUIComponents();
        buttonRisolviCruciverba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTime=System.currentTimeMillis();

                if (cruciverba1.risolviCruciverba()) {
                    listListaParole.setListData(cruciverba1.dizionario.toArray());
                    stopTime=System.currentTimeMillis();
                    totalTime=stopTime-startTime;
                    JOptionPane.showMessageDialog(null, "Cruciverba completato in " + totalTime, "Risultato cruciverba", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    listListaParole.setListData(cruciverba1.dizionario.toArray());
                    JOptionPane.showMessageDialog(null, "Cruciverba non completato", "Risultato cruciverba", JOptionPane.ERROR_MESSAGE);
                }
                /*//risoluzione tramite algoritmo3
                if (cruciverba1.risolviCruciverba_alg3()) {
                    listListaParole.setListData(cruciverba1.dizionario.toArray());
                    JOptionPane.showMessageDialog(null, "Cruciverba completato", "Risultato cruciverba", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    listListaParole.setListData(cruciverba1.dizionario.toArray());
                    JOptionPane.showMessageDialog(null, "Cruciverba non completato", "Risultato cruciverba", JOptionPane.ERROR_MESSAGE);
                }
                 */
            }
        });
        buttonCercaParola.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //TODO lanciare inserisci1Parola al click
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

        /*//esempio1
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
*/

        //esempio2
        dizionarioInput = new ArrayList<String>();
        //per prova parole in più nella lista da inserire
        dizionarioInput.add("CASABLANCA");
        dizionarioInput.add("QUAT");
        dizionarioInput.add("TRE");
        dizionarioInput.add("BOSTRN");
        dizionarioInput.add("DD");
        dizionarioInput.add("CIAO");
        dizionarioInput.add("TOPO");
        dizionarioInput.add("INORGANICI");
        dizionarioInput.add("TESIO");
        dizionarioInput.add("TOCCIRE");
        dizionarioInput.add("TORO");


        dizionarioInput.add("STRAGRANDE");
        dizionarioInput.add("TESEO");
        dizionarioInput.add("CASABIANCA");
        dizionarioInput.add("ALAIN");
        dizionarioInput.add("FE");
        dizionarioInput.add("FURIA");
        dizionarioInput.add("ROSSO");
        dizionarioInput.add("ALVARO");
        dizionarioInput.add("ONLUS");
        dizionarioInput.add("LEZIOSA");
        dizionarioInput.add("BISTRO");
        dizionarioInput.add("PIETA");
        dizionarioInput.add("ESTERE");
        dizionarioInput.add("DENTISTICO");
        dizionarioInput.add("LASER");
        dizionarioInput.add("PORO");
        dizionarioInput.add("TIZIO");
        dizionarioInput.add("OSTETRICA");
        dizionarioInput.add("MOSA");
        dizionarioInput.add("ANA");
        dizionarioInput.add("MALESIA");
        dizionarioInput.add("AN");
        dizionarioInput.add("LEONINO");
        dizionarioInput.add("BEN");
        dizionarioInput.add("TE");
        dizionarioInput.add("FARETTO");
        dizionarioInput.add("RIO");
        dizionarioInput.add("GARRANI");
        dizionarioInput.add("WO");
        dizionarioInput.add("LISI");
        dizionarioInput.add("GARA");
        dizionarioInput.add("MARGARINE");
        dizionarioInput.add("INORGANICA");
        dizionarioInput.add("ALATO");
        dizionarioInput.add("FERRAMENTA");
        dizionarioInput.add("CRANICO");
        dizionarioInput.add("RA");
        dizionarioInput.add("RAVERA");
        dizionarioInput.add("TOCCARE");
        dizionarioInput.add("OCARINA");
        dizionarioInput.add("BOB");
        dizionarioInput.add("LIONE");
        dizionarioInput.add("ODORE");
        dizionarioInput.add("ADAMI");
        dizionarioInput.add("SCAPPATOIA");
        dizionarioInput.add("ALIBI");
        dizionarioInput.add("LORENA");
        dizionarioInput.add("MASSA");
        dizionarioInput.add("SAVONAROLA");
        dizionarioInput.add("IRENE");
        dizionarioInput.add("WESER");
        dizionarioInput.add("BOSTON");
        dizionarioInput.add("BE");


        /*//esempio3
        dizionarioInput = new ArrayList<String>();
        dizionarioInput.add("BANALITA");
        dizionarioInput.add("MIAO");
        dizionarioInput.add("TETTO");
        dizionarioInput.add("ISOLA");
        dizionarioInput.add("LUCI");
        dizionarioInput.add("IDA");
        dizionarioInput.add("TONANTE");
        dizionarioInput.add("EO");
        dizionarioInput.add("NOMINE");
        dizionarioInput.add("NAT");
        dizionarioInput.add("SPIEGAMENTO");
        dizionarioInput.add("TA");
        dizionarioInput.add("OTRI");
        dizionarioInput.add("ADATTE");
        dizionarioInput.add("EOLO");
        dizionarioInput.add("AGARICO");
        dizionarioInput.add("IDIOMA");
        dizionarioInput.add("PECORE");
        dizionarioInput.add("GIARDINIPUBBLICI");
        dizionarioInput.add("ESIGUI");
        dizionarioInput.add("AI");
        dizionarioInput.add("ELI");
        dizionarioInput.add("MANICOTTO");
        dizionarioInput.add("ITER");
        dizionarioInput.add("ARRINGARE");
        dizionarioInput.add("TI");
        dizionarioInput.add("MARINOMARINI");
        dizionarioInput.add("FIDEL");
        dizionarioInput.add("INEDITI");
        dizionarioInput.add("TOM");
        dizionarioInput.add("NI");
        dizionarioInput.add("BARATRO");
        dizionarioInput.add("LARIO");
        dizionarioInput.add("ULANI");
        dizionarioInput.add("ATEO");
        dizionarioInput.add("ON");
        dizionarioInput.add("CALLIMACO");
        dizionarioInput.add("NEO");
        dizionarioInput.add("ISACCO");
        dizionarioInput.add("TINI");
        dizionarioInput.add("RINASCIMENTO");
        dizionarioInput.add("LB");
        dizionarioInput.add("ADRIA");
        dizionarioInput.add("SL");
        dizionarioInput.add("NC");
        dizionarioInput.add("PIRAMIDE");
        dizionarioInput.add("ALI");
        dizionarioInput.add("ADA");
        dizionarioInput.add("ILIO");
        dizionarioInput.add("AU");
        dizionarioInput.add("ECONOMICA");
        dizionarioInput.add("ICE");
        dizionarioInput.add("SUD");
        dizionarioInput.add("AREA");
        dizionarioInput.add("RAME");
        dizionarioInput.add("MB");
        dizionarioInput.add("CALAF");
        dizionarioInput.add("BUE");
        dizionarioInput.add("EMI");
        dizionarioInput.add("ASTRONOMICA");
        dizionarioInput.add("CRUMIRA");
        dizionarioInput.add("OZI");
        dizionarioInput.add("RINOMATA");
        dizionarioInput.add("ANOMALIA");
        dizionarioInput.add("IDIOTI");
        dizionarioInput.add("LOS");
        dizionarioInput.add("SS");
        dizionarioInput.add("URNE");
        dizionarioInput.add("ORTI");
        dizionarioInput.add("RA");
        dizionarioInput.add("PUBBLICAZIONI");
        dizionarioInput.add("SE");
        */



        //window.createUIComponents();
        window.open();
        frame.setContentPane(window.panelMain);

    }

    public void open() {

        //creazione cruciverba per l'utilizzo di funzioni dell'algoritmo1
        /*//esempio1
        cruciverba1 = new ImplAlg1Cruciverba(panelMain, matrice, "CANE", 0, 0, dizionarioInput,'O');
        */

        /*//esempio2
        cruciverba1=new ImplAlg1Cruciverba(panelMain,matrice,"TERRORISTA",7,2,dizionarioInput,'O');
*/

         /*//esempio3
        cruciverba1=new ImplAlg1Cruciverba(panelMain,matrice,"DONO",4,4,dizionarioInput,'V');
        */

        //creazione cruciverba per l'utilizzo di funzioni dell'algoritmo2
         /*//esempio1
        cruciverba1 = new ImplAlg2Cruciverba(panelMain, matrice, "CANE", 0, 0, dizionarioInput,'O');
        */

        /*//esempio2
        cruciverba1=new ImplAlg2Cruciverba(panelMain,matrice,"TERRORISTA",7,2,dizionarioInput,'O');
*/

         /*//esempio3
        cruciverba1=new ImplAlg2Cruciverba(panelMain,matrice,"DONO",4,4,dizionarioInput,'V');
        */

        //creazione cruciverba per l'utilizzo di funzioni dell'algoritmo4
        /*//esempio1
        cruciverba1=new ImplAlg4Cruciverba_AI(panelMain,matrice,"CANE",0,0, dizionarioInput,'O');
         */

        //esempio2
        cruciverba1 = new ImplAlg4Cruciverba_AI(panelMain,matrice, "TERRORISTA", 7, 2, dizionarioInput,'O');


        /*//esempio3
        cruciverba1= new ImplAlg4Cruciverba_AI(panelMain, matrice, "DONO",4,4,dizionarioInput, 'V');
*/
        listListaParole.setListData(dizionarioInput.toArray());

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
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
