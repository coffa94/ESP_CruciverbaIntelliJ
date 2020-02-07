import javax.swing.*;

public class Interfaccia {
    private JLabel cruciverbaLabel;
    private JButton risolviCruciverbaButton;
    private JButton cercaParolaButton;

    public static void main(String[] args) {
Interfaccia window = new Interfaccia();
window.open();

    }

    public void open() {
        Display display = Display.getDefault();
        createContents(matrice);
        cruciverba1=new ImplementazioneCruciverba(matrice, "CANE",0,0,dizionarioInput);
        aggiornaCruciverba(cruciverba1.VisualizzaSchema());
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}
