package circulo;
import ponto.PontoGr;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Classe que representa um círculo gráfico capaz de ser desenhado na tela.
 * 
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 */
public class CirculoGr extends Circulo
{
    /** Cor do círculo. */
    Color corCirculo = Color.BLACK;

    /** Nome identificador do círculo. */
    String nomeCirculo = "";

    /** Cor do nome do círculo. */
    Color corNomeCirculo  = Color.BLACK;

    /** Espessura do traço do círculo. */
    int espReta = 1;
    
    /**
     * Construtor de CirculoGr a partir de dois pontos que definem o centro e a borda.
     * 
     * @param x1 Coordenada x do centro
     * @param y1 Coordenada y do centro
     * @param x2 Coordenada x de um ponto na borda do círculo
     * @param y2 Coordenada y de um ponto na borda do círculo
     * @param cor Cor do círculo
     * @param nome Nome do círculo
     * @param esp Espessura do traço do círculo
     */
    public CirculoGr(int x1, int y1, int x2, int y2, Color cor, String nome, int esp){
        super (x1, y1, (int)Math.hypot(x2-x1, y2-y1));
        corCirculo = cor;
        nomeCirculo = nome;
        espReta = esp;
    }   
    
    /**
     * Método para desenhar o círculo usando o algoritmo de Bresenham (Midpoint).
     * Isso garante que toda a circunferência seja feita de pontos (sem falhas na espessura).
     * 
     * @param g Contexto gráfico onde o círculo será desenhado
     */
    public void desenharCirculo(Graphics g){
        int xc = (int)getCentro().getX();
        int yc = (int)getCentro().getY();
        int r = getRaio();

        int x = 0;
        int y = r;
        int d = 3 - 2 * r;

        desenharPontosCirculo(g, xc, yc, x, y);

        while (y >= x) {
            x++;
            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else {
                d = d + 4 * x + 6;
            }
            desenharPontosCirculo(g, xc, yc, x, y);
        }
    }
    
    /**
     * Desenha os 8 pontos simétricos do círculo em relação ao centro.
     * 
     * @param g Contexto gráfico
     * @param xc Coordenada x do centro
     * @param yc Coordenada y do centro
     * @param x Deslocamento x relativo ao centro
     * @param y Deslocamento y relativo ao centro
     */
    private void desenharPontosCirculo(Graphics g, int xc, int yc, int x, int y) {
        plotarPonto(g, xc + x, yc + y);
        plotarPonto(g, xc - x, yc + y);
        plotarPonto(g, xc + x, yc - y);
        plotarPonto(g, xc - x, yc - y);
        plotarPonto(g, xc + y, yc + x);
        plotarPonto(g, xc - y, yc + x);
        plotarPonto(g, xc + y, yc - x);
        plotarPonto(g, xc - y, yc - x);
    }

    /**
     * Instancia e desenha um PontoGr na tela.
     * 
     * @param g Contexto gráfico
     * @param x Coordenada x do ponto
     * @param y Coordenada y do ponto
     */
    private void plotarPonto(Graphics g, int x, int y) {
        PontoGr p = new PontoGr(x, y, corCirculo, espReta);
        p.desenharPonto(g);
    }
    
    /**
     * Retorna a cor do círculo.
     * 
     * @return Cor do círculo
     */
    public Color getCorCirculo() { return corCirculo; }

    /**
     * Retorna a espessura do traço do círculo.
     * 
     * @return Espessura do traço
     */
    public int getEspReta() { return espReta; }
}
