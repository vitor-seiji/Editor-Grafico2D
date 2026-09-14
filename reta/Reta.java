package reta;
import ponto.*;
/**
 * Representacao matematica de um segmento de reta no espaco bidimensional (2D).
 * Definida por dois pontos extremos (ponto inicial p1 e ponto final p2).
 * Fornece metodos para calculo dos coeficientes angular (m) e linear (b)
 * da equacao reduzida da reta (y = mx + b).
 *
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 */
public class Reta {
 
    /**
     * Ponto inicial do segmento de reta.
     */
    public Ponto p1;

    /**
     * Ponto final do segmento de reta.
     */
    public Ponto p2;

    /**
     * Constroi uma reta a partir de coordenadas inteiras para os pontos p1 e p2.
     *
     * @param x1 Coordenada x do ponto inicial (p1)
     * @param y1 Coordenada y do ponto inicial (p1)
     * @param x2 Coordenada x do ponto final (p2)
     * @param y2 Coordenada y do ponto final (p2)
     */
    public Reta(int x1, int y1, int x2, int y2) {
        setP1(new Ponto(x1, y1));
        setP2(new Ponto(x2, y2));
    }
    
    /**
     * Constroi uma reta a partir de coordenadas reais (double) para os pontos p1 e p2.
     *
     * @param x1 Coordenada x do ponto inicial (p1)
     * @param y1 Coordenada y do ponto inicial (p1)
     * @param x2 Coordenada x do ponto final (p2)
     * @param y2 Coordenada y do ponto final (p2)
     */
    public Reta(double x1, double y1, double x2, double y2) {
        setP1(new Ponto(x1, y1));
        setP2(new Ponto(x2, y2));
    }
    
    /**
     * Constroi uma reta a partir de dois pontos externos ja existentes.
     *
     * @param p1 Ponto inicial da reta
     * @param p2 Ponto final da reta
     */
    public Reta(Ponto p1, Ponto p2) {
        setP1(p1);
        setP2(p2);
    }
    
    /**
     * Constroi uma reta a partir de outra reta existente (construtor de copia).
     *
     * @param r Reta a ser copiada
     */
    public Reta (Reta r){
        setP1(r.getP1());
        setP2(r.getP2());
    }
    
    /**
     * Altera o ponto inicial (p1) da reta.
     *
     * @param p Novo ponto inicial (p1)
     */
    public void setP1(Ponto p){
        this.p1 = p;
    }
    
    /**
     * Altera o ponto final (p2) da reta.
     *
     * @param p Novo ponto final (p2)
     */
    public void setP2(Ponto p){
        this.p2 = p;
    }
    
    /**
     * Retorna o ponto inicial (p1) da reta.
     *
     * @return Ponto inicial (p1)
     */
    public Ponto getP1(){
        return this.p1;
    }
    
    /**
     * Retorna o ponto final (p2) da reta.
     *
     * @return Ponto final (p2)
     */
    public Ponto getP2(){
        return this.p2;
    }

    /**
     * Calcula o coeficiente angular (m) da equacao reduzida da reta (y = mx + b).
     * Formula: m = (y2 - y1) / (x2 - x1).
     *
     * @return O valor do coeficiente angular m
     */
    public double calcularM(){
        // m = (y2-y1)/(x2-x1)
        double m = (getP2().getY() - getP1().getY())/(getP2().getX() - getP1().getX());
        return m;
    }

    /**
     * Calcula o coeficiente linear (b) da equacao reduzida da reta (y = mx + b).
     * Formula: b = y1 - m * x1.
     *
     * @return O valor do coeficiente linear b
     */
    public double calcularB(){
        //b = y1 - mx1
        double b = getP1().getY() - calcularM()*getP1().getX();
        return b;
    }
    
    /**
     * Retorna uma representacao textual da reta, exibindo as coordenadas de p1, p2
     * e a sua equacao reduzida no formato y = mx + b.
     *
     * @return String representando a reta
     */
    @Override
    public String toString(){
        String s = "P1: " + getP1().toString() + " P2: " + getP2().toString();
        s = s + "\nEq. da reta: y = " + calcularM() + "*x + " + calcularB();
        return s;
    }
   
}

