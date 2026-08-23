package reta;
import ponto.*;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Desenha figuras com retas.
 * 
 * @author Julio Arakaki 
 * @version 20220815
 */
public class FiguraReta {
    
    /**
     * Desenha uma reta simples definindo a cor e espessura padrao
     */
    public static void desenharReta(Graphics g, int x1, int y1, int x2, int y2) {
        // Define uma cor visivel (ex: Preta) e espessura 2
        RetaGr r = new RetaGr(x1, y1, x2, y2, Color.BLACK, "", 20);
        
        // Usando o algoritmo Midpoint/Bresenham
        r.desenharRetaMp(g); 
    }

    /**
     * Sobrecarga do metodo para permitir escolher a cor e espessura
     */
    public static void desenharReta(Graphics g, int x1, int y1, int x2, int y2, Color cor, int esp) {
        RetaGr r = new RetaGr(x1, y1, x2, y2, cor, "", esp);
        r.desenharRetaMp(g);
    }

    /**
     * Desenha varias retas na area de desenho
     */
    public static void desenharRetas(Graphics g, int qtde, int esp) {
        for(int i = 0; i < qtde; i++) {
            int x1 = (int) (Math.random() * 801);
            int y1 = (int) (Math.random() * 801);
            int x2 = (int) (Math.random() * 801);
            int y2 = (int) (Math.random() * 801);

            Color cor = new Color((int) (Math.random() * 256),  
                                  (int) (Math.random() * 256),  
                                  (int) (Math.random() * 256));
            RetaGr r = new RetaGr(x1, y1, x2, y2, cor, "", esp);
            r.desenharRetaMp(g);
        }
    }
}
