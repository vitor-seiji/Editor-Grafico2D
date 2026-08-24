import circulo.*;
import ponto.*;
import reta.*;

/**
 * Classe main da aplicação.
 * 
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 * @version 
 */
public class App {
    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Gui(1000, 900); // define dimensao da janela (em pixels)
    }
    
}

