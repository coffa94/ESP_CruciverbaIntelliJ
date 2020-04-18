package com.cruciverbapackage;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImplAlg4Cruciverba_AI extends ImplementazioneCruciverba{
    private CSP constraintsSolver;
    private ArrayList<Parola> listSolution;

    //costruttore cruciverba con una struttura passata in input
    public ImplAlg4Cruciverba_AI(JPanel panel, char matrice[][], String parolaIniziale, int posizioneRigaIniziale, int posizioneColonnaIniziale, ArrayList<String> dizionarioInput) {
        super(panel,matrice,parolaIniziale,posizioneRigaIniziale,posizioneColonnaIniziale,dizionarioInput);
        constraintsSolver= new CSP(schema_originale,dizionario);
    }

    //corrisponde ad un ciclo di risolviCruciverba (in cui poi viene lanciata la funzione cercaParolaDaInserire)
    //@requires this!=null
    //@effects: inserisce una parola nello schema del cruciverba
    //@throws: nullPointerException
    //*return: true se cruciverba è completo, false se non è stato completato o non è stata trovata una parola da inserire
    public boolean inserisci1Parola(){ return true; }

    //TODO implementare algoritmo 4 con AI utilizzo CSP
    //@requires: this!=null
    //@effects: chiama cercaParolaDaInserire finche lo schema non è completato, cioè isComplete=true
    //@throws: nullPointerException
    //@return: true se completato, false se non è possibile completarlo
    public boolean risolviCruciverba(){

        constraintsSolver.solve();
        if (!(constraintsSolver.isCSPExecuted())){


            if (constraintsSolver.isCSPResult()){
                listSolution=constraintsSolver.getListSolution();
                while(listSolution.size()>0){

                }
            }
            return constraintsSolver.isCSPResult();
        }else{
            if(constraintsSolver.isCSPResult()) {
                listSolution=constraintsSolver.getListSolution();
                while (listSolution.size() > 0) {

                }
            }
            return constraintsSolver.isCSPResult();

        }
    }


}
