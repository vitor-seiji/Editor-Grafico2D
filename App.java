import circulo.*;
import ponto.*;
import reta.*;

/**
 * Escreva a descriÃ§Ã£o da classe Aplicacao aqui.
 * 
 * @author (seu nome) 
 * @version (nÃºmero de versÃ£o ou data)
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

