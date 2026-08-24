package circulo;
import ponto.PontoGr;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Classe que representa um circulo grafico
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 */
public class CirculoGr extends Circulo
{
    Color corCirculo = Color.BLACK;
    String nomeCirculo = "";
    Color corNomeCirculo  = Color.BLACK;
    int espReta = 1;
    
    /**
     * Construtor de CirculoGr
     */
    public CirculoGr(int x1, int y1, int x2, int y2, Color cor, String nome, int esp){
        super (x1, y1, (int)Math.hypot(x2-x1, y2-y1));
        corCirculo = cor;
        nomeCirculo = nome;
        espReta = esp;
    }   
    
    /**
     * Metodo para desenhar o circulo usando o algoritmo de Bresenham (Midpoint)
     * Isso garante que toda a circunferencia seja feita de pontos (sem falhas na espessura)
     * @param g Contexto grafico
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
     * Desenha os 8 pontos simetricos do circulo
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
     * Instancia e desenha um PontoGr na tela
     */
    private void plotarPonto(Graphics g, int x, int y) {
        PontoGr p = new PontoGr(x, y, corCirculo, espReta);
        p.desenharPonto(g);
    }
}
