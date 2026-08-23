package circulo;
import ponto.*;
//Testando o git
public class Circulo
{
    // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    private Ponto centro;
    private int raio;

    /**
     * Construtor para objetos da classe Circulo
     */
    public Circulo(double x, double y, int raio)
    {
        centro = new Ponto(x, y);
        this.raio = raio;
    }
    
    public Ponto getCentro(){
        return centro;
    }
    
    public int getRaio(){
        return raio;
    }
    
    public void setCentro(Ponto centro){
        this.centro = centro;
    }
    
    public void setRaio(int raio){
        this.raio = raio;
    }
    

}