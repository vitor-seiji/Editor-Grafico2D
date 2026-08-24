package retangulo;
import ponto.Ponto;

/**
 * Classe que representa um Retangulo
 * 
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida

 */
public class Retangulo {
    private Ponto p1; // Canto superior esquerdo
    private Ponto p2; // Canto inferior direito

    /**
     * Construtor
     */
    public Retangulo(double x1, double y1, double x2, double y2) {
        this.p1 = new Ponto(x1, y1);
        this.p2 = new Ponto(x2, y2);
    }
    
    public Ponto getP1() { return p1; }
    public Ponto getP2() { return p2; }
}
