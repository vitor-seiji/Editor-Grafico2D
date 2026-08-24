package triangulo;
import ponto.Ponto;

/**
 * Classe que representa um Triangulo
 *
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
*/
public class Triangulo {
    private Ponto p1, p2, p3;

    /**
     * Construtor
     */
    public Triangulo(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.p1 = new Ponto(x1, y1);
        this.p2 = new Ponto(x2, y2);
        this.p3 = new Ponto(x3, y3);
    }
    
    public Ponto getP1() { return p1; }
    public Ponto getP2() { return p2; }
    public Ponto getP3() { return p3; }
}
