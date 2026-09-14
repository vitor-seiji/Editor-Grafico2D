package triangulo;
import ponto.Ponto;

/**
 * Classe que representa um Triângulo no plano bidimensional definido por três vértices.
 *
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 */
public class Triangulo {
    /** Vértices do triângulo (p1, p2 e p3). */
    private Ponto p1, p2, p3;

    /**
     * Construtor da classe Triangulo a partir das coordenadas de seus três vértices.
     * 
     * @param x1 Coordenada x do primeiro vértice
     * @param y1 Coordenada y do primeiro vértice
     * @param x2 Coordenada x do segundo vértice
     * @param y2 Coordenada y do segundo vértice
     * @param x3 Coordenada x do terceiro vértice
     * @param y3 Coordenada y do terceiro vértice
     */
    public Triangulo(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.p1 = new Ponto(x1, y1);
        this.p2 = new Ponto(x2, y2);
        this.p3 = new Ponto(x3, y3);
    }
    
    /**
     * Retorna o primeiro vértice do triângulo.
     * 
     * @return Primeiro vértice (Ponto)
     */
    public Ponto getP1() { return p1; }

    /**
     * Retorna o segundo vértice do triângulo.
     * 
     * @return Segundo vértice (Ponto)
     */
    public Ponto getP2() { return p2; }

    /**
     * Retorna o terceiro vértice do triângulo.
     * 
     * @return Terceiro vértice (Ponto)
     */
    public Ponto getP3() { return p3; }
}
