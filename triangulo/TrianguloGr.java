package triangulo;
import java.awt.Color;
import java.awt.Graphics;
import reta.RetaGr;

/**
 * Classe que representa um Triangulo grafico
 * 
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 */
public class TrianguloGr extends Triangulo {
    Color corTriangulo = Color.BLACK;
    int espessura = 1;

    /**
     * Construtor
     */
    public TrianguloGr(int x1, int y1, int x2, int y2, int x3, int y3, Color cor, int espessura) {
        super(x1, y1, x2, y2, x3, y3);
        this.corTriangulo = cor;
        this.espessura = espessura;
    }

    /**
     * Desenha o triangulo
     */
    public void desenharTriangulo(Graphics g) {
        // Usa RetaGr para aproveitar o algoritmo de espessura e desenho de retas
        RetaGr r1 = new RetaGr((int)getP1().getX(), (int)getP1().getY(), (int)getP2().getX(), (int)getP2().getY(), corTriangulo, espessura);
        RetaGr r2 = new RetaGr((int)getP2().getX(), (int)getP2().getY(), (int)getP3().getX(), (int)getP3().getY(), corTriangulo, espessura);
        RetaGr r3 = new RetaGr((int)getP3().getX(), (int)getP3().getY(), (int)getP1().getX(), (int)getP1().getY(), corTriangulo, espessura);
        
        r1.desenharRetaMp(g);
        r2.desenharRetaMp(g);
        r3.desenharRetaMp(g);
    }
    
    public Color getCorTriangulo() { return corTriangulo; }
    public int getEspessura() { return espessura; }
}
