package ponto;
/**
 * Representacao de um ponto matematico no espaco bidimensional (2D).
 * Armazena as coordenadas cartesianas x e y como valores de ponto flutuante de precisao dupla.
 * Fornece metodos de acesso, modificacao e calculo de distancia euclidiana entre pontos.
 * 
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 */
public class Ponto {

    /**
     * Coordenada horizontal (eixo X) do ponto.
     */
    private double x;

    /**
     * Coordenada vertical (eixo Y) do ponto.
     */
    private double y;

    /**
     * Constroi um ponto padrao posicionado na origem do sistema de coordenadas (0, 0).
     */
    public Ponto() {
        setX(0);
        setY(0);
    }

    /**
     * Constroi um ponto copiando as coordenadas de outro ponto existente.
     *
     * @param p Ponto a ser copiado
     */
    public Ponto(Ponto p) {
        setX(p.getX());
        setY(p.getY());
    }

    /**
     * Constroi um ponto com coordenadas cartesianas especificas.
     *
     * @param x Coordenada no eixo X
     * @param y Coordenada no eixo Y
     */
    public Ponto(double x, double y) {
        setX(x);
        setY(y);
    }
    
    /**
     * Retorna a coordenada horizontal x do ponto.
     *
     * @return O valor da coordenada x
     */
    public double getX() {
        return x;
    }

    /**
     * Define a coordenada horizontal x do ponto.
     *
     * @param x Novo valor da coordenada x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Retorna a coordenada vertical y do ponto.
     *
     * @return O valor da coordenada y
     */
    public double getY() {
        return y;
    }

    /**
     * Define a coordenada vertical y do ponto.
     *
     * @param y Novo valor da coordenada y
     */
    public void setY(double y) {
        this.y = y;
    }
    
    /**
     * Calcula a distancia euclidiana entre este ponto e outro ponto fornecido.
     * 
     * @param p Ponto externo para calculo da distancia
     * @return O valor da distancia euclidiana entre os dois pontos
     */
    public double calcularDistancia(Ponto p) {
        
        double d = Math.sqrt(Math.pow(p.getY()-getY(), 2) + Math.pow(p.getX()-getX(), 2));
        
        return(d);
    
    }

    /**
     * Retorna a representacao em String do ponto no formato "Ponto [x, y]".
     *
     * @return Representacao textual do ponto
     */
    @Override
    public String toString() {
        return "Ponto [" + getX() + ", " + getY() +  "]";
    }
}


