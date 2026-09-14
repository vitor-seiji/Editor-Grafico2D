package retangulo;
import ponto.Ponto;

/**
 * Classe que representa um Retângulo no plano bidimensional definido por dois pontos opostos.
 * 
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 */
public class Retangulo {
    /** Ponto que representa o primeiro vértice (canto superior esquerdo). */
    private Ponto p1;

    /** Ponto que representa o vértice oposto (canto inferior direito). */
    private Ponto p2;

    /**
     * Construtor da classe Retangulo a partir das coordenadas de dois vértices opostos.
     * 
     * @param x1 Coordenada x do primeiro ponto
     * @param y1 Coordenada y do primeiro ponto
     * @param x2 Coordenada x do segundo ponto
     * @param y2 Coordenada y do segundo ponto
     */
    public Retangulo(double x1, double y1, double x2, double y2) {
        this.p1 = new Ponto(x1, y1);
        this.p2 = new Ponto(x2, y2);
    }
    
    /**
     * Retorna o primeiro ponto do retângulo.
     * 
     * @return Primeiro ponto (Ponto)
     */
    public Ponto getP1() { return p1; }

    /**
     * Retorna o segundo ponto do retângulo.
     * 
     * @return Segundo ponto (Ponto)
     */
    public Ponto getP2() { return p2; }
}
