import java.awt.Color;
import java.awt.Graphics;
/**
 * Escreva uma descrição da classe CirculoGr aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class CirculoGr extends Circulo
{
    Color corCirculo = Color.BLACK;   // cor da reta
    String nomeCirculo = ""; // nome da reta
    Color corNomeCirculo  = Color.BLACK;
    int espReta = 1; // espessura da reta
    
    public CirculoGr(int x1, int y1, int x2, int y2, Color cor, String nome, int esp){
        super (x1, y1, x2, y2);
        setCorCirculo(cor);
        setEspReta(esp);
    }   
    
    
}