import circulo.*;
import ponto.*;
import reta.*;

/**
 * Classe principal da aplicação do Editor Gráfico.
 * Inicializa a interface gráfica definindo o Look and Feel do sistema
 * e instanciando a janela principal com as dimensões especificadas.
 * 
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 */
public class App {
    /**
     * Ponto de entrada principal da aplicação.
     * Configura o Look and Feel para o padrão do sistema operacional
     * e instancia a janela principal (Gui) com resolução de 1000x900 pixels.
     * 
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Gui(1000, 900); // define dimensao da janela (em pixels)
    }
    
}

