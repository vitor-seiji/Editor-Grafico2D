package retangulo;
import java.awt.Color;
import java.awt.Graphics;
import reta.RetaGr;

/**
 * Classe que representa um Retangulo grafico
 * 
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 */
public class RetanguloGr extends Retangulo {
    Color corRetangulo = Color.BLACK;
    int espessura = 1;

    /**
     * Construtor
     */
    public RetanguloGr(int x1, int y1, int x2, int y2, Color cor, int espessura) {
        super(x1, y1, x2, y2);
        this.corRetangulo = cor;
        this.espessura = espessura;
    }

    /**
     * Desenha o retangulo
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
    
    public Color getCorRetangulo() { return corRetangulo; }
    public int getEspessura() { return espessura; }
}
