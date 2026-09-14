package triangulo;
import java.awt.Color;
import java.awt.Graphics;
import reta.RetaGr;

/**
 * Classe que representa um Triângulo gráfico capaz de ser desenhado na tela.
 * 
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 */
public class TrianguloGr extends Triangulo {
    /** Cor do triângulo. */
    Color corTriangulo = Color.BLACK;

    /** Espessura das arestas do triângulo. */
    int espessura = 1;

    /**
     * Construtor de TrianguloGr definindo as coordenadas dos três vértices, a cor e a espessura.
     * 
     * @param x1 Coordenada x do primeiro vértice
     * @param y1 Coordenada y do primeiro vértice
     * @param x2 Coordenada x do segundo vértice
     * @param y2 Coordenada y do segundo vértice
     * @param x3 Coordenada x do terceiro vértice
     * @param y3 Coordenada y do terceiro vértice
     * @param cor Cor do triângulo
     * @param espessura Espessura das arestas do triângulo
     */
    public TrianguloGr(int x1, int y1, int x2, int y2, int x3, int y3, Color cor, int espessura) {
        super(x1, y1, x2, y2, x3, y3);
        this.corTriangulo = cor;
        this.espessura = espessura;
    }

    /**
     * Desenha o triângulo na tela traçando suas três arestas com o algoritmo MidPoint.
     * 
     * @param g Contexto gráfico onde o triângulo será desenhado
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
    
    /**
     * Retorna a cor do triângulo.
     * 
     * @return Cor do triângulo
     */
    public Color getCorTriangulo() { return corTriangulo; }

    /**
     * Retorna a espessura das arestas do triângulo.
     * 
     * @return Espessura das arestas
     */
    public int getEspessura() { return espessura; }
}
