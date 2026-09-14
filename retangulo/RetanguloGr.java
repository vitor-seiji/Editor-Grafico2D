package retangulo;
import java.awt.Color;
import java.awt.Graphics;
import reta.RetaGr;

/**
 * Classe que representa um Retângulo gráfico capaz de ser desenhado na tela.
 * 
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 */
public class RetanguloGr extends Retangulo {
    /** Cor do retângulo. */
    Color corRetangulo = Color.BLACK;

    /** Espessura das bordas do retângulo. */
    int espessura = 1;

    /**
     * Construtor de RetanguloGr definindo as coordenadas dos dois vértices, a cor e a espessura.
     * 
     * @param x1 Coordenada x do primeiro ponto
     * @param y1 Coordenada y do primeiro ponto
     * @param x2 Coordenada x do segundo ponto
     * @param y2 Coordenada y do segundo ponto
     * @param cor Cor do retângulo
     * @param espessura Espessura das bordas do retângulo
     */
    public RetanguloGr(int x1, int y1, int x2, int y2, Color cor, int espessura) {
        super(x1, y1, x2, y2);
        this.corRetangulo = cor;
        this.espessura = espessura;
    }

    /**
     * Desenha o retângulo na tela traçando suas quatro bordas com o algoritmo MidPoint.
     * 
     * @param g Contexto gráfico onde o retângulo será desenhado
     */
    public void desenharRetangulo(Graphics g) {
        int xMin = (int)Math.min(getP1().getX(), getP2().getX());
        int yMin = (int)Math.min(getP1().getY(), getP2().getY());
        int xMax = (int)Math.max(getP1().getX(), getP2().getX());
        int yMax = (int)Math.max(getP1().getY(), getP2().getY());
        
        RetaGr r1 = new RetaGr(xMin, yMin, xMax, yMin, corRetangulo, espessura);
        RetaGr r2 = new RetaGr(xMax, yMin, xMax, yMax, corRetangulo, espessura);
        RetaGr r3 = new RetaGr(xMax, yMax, xMin, yMax, corRetangulo, espessura);
        RetaGr r4 = new RetaGr(xMin, yMax, xMin, yMin, corRetangulo, espessura);
        
        r1.desenharRetaMp(g);
        r2.desenharRetaMp(g);
        r3.desenharRetaMp(g);
        r4.desenharRetaMp(g);
    }
    
    /**
     * Retorna a cor do retângulo.
     * 
     * @return Cor do retângulo
     */
    public Color getCorRetangulo() { return corRetangulo; }

    /**
     * Retorna a espessura das bordas do retângulo.
     * 
     * @return Espessura das bordas
     */
    public int getEspessura() { return espessura; }
}
