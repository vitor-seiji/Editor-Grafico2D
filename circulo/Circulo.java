package circulo;
import ponto.*;

/**
 * Classe que representa um círculo matemático no plano bidimensional.
 * 
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 */
public class Circulo
{
    /** Ponto central do círculo. */
    private Ponto centro;

    /** Raio do círculo. */
    private int raio;

    /**
     * Construtor para objetos da classe Circulo.
     * 
     * @param x Coordenada x do centro do círculo
     * @param y Coordenada y do centro do círculo
     * @param raio Raio do círculo
     */
    public Circulo(double x, double y, int raio)
    {
        centro = new Ponto(x, y);
        this.raio = raio;
    }
    
    /**
     * Retorna o ponto central do círculo.
     * 
     * @return Ponto central do círculo
     */
    public Ponto getCentro(){
        return centro;
    }
    
    /**
     * Retorna o raio do círculo.
     * 
     * @return Raio do círculo
     */
    public int getRaio(){
        return raio;
    }
    
    /**
     * Altera o ponto central do círculo.
     * 
     * @param centro Novo ponto central do círculo
     */
    public void setCentro(Ponto centro){
        this.centro = centro;
    }
    
    /**
     * Altera o raio do círculo.
     * 
     * @param raio Novo valor do raio do círculo
     */
    public void setRaio(int raio){
        this.raio = raio;
    }
    

}