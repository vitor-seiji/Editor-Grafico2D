package circulo;
import ponto.*;
import java.awt.Color;
import java.awt.Graphics;

public class CirculoGr extends Circulo
{
    Color corCirculo = Color.BLACK;
    String nomeCirculo = "";
    Color corNomeCirculo  = Color.BLACK;
    int espReta = 1;
    
    public CirculoGr(int x1, int y1, int x2, int y2, Color cor, String nome, int esp){
        super (x1, y1, (int)Math.hypot(x2-x1, y2-y1));
        corCirculo = cor;
        espReta = esp;
    }   
}
