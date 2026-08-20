import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 * Painel para desenhar primitivos graficos
 * 
 * @author Julio 
 * @version 20260803
 */
public class PainelDesenho extends JPanel implements MouseListener, MouseMotionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    JLabel msg;
    TiposPrimitivos tipo;
    int divisoes;
    int xMouse, yMouse;
    int x1, y1, x2, y2;
    boolean primeiraVez = true;
    int nPto = 1;

    /**
     * COnstrutor para objetos da classe PainelDesenho
     */
    public PainelDesenho(JLabel msg, TiposPrimitivos tipo) {
        this.tipo = tipo;
        this.msg = msg;
        //       this.setBackground(Color.black);
        this.addMouseListener(this); 
        this.addMouseMotionListener(this);

    }
    public void setTipo(TiposPrimitivos tipo){
        this.tipo = tipo;
        primeiraVez = true;
    }

    public TiposPrimitivos getTipo(){
        return this.tipo;
    }

    /**
     * paintComponent - metodo para desenhar
     *
     * @param g A parameter
     */
    public void paintComponent(Graphics g) {   
        
            if(this.tipo == TiposPrimitivos.PONTO){
                FiguraPontos.desenharPonto(g, xMouse, yMouse, "", 10);
            }
            if(this.tipo == TiposPrimitivos.RETA){
                
                FiguraReta.desenharReta(g, x1, y1, x2, y2);
        
                /*
                int xRC = x1, yR;
                int xRF = x2;
                if(x2 < x1){
                    xRC = x2;
                    xRF = x1;
                }
                double m = (double)(y1 - y2)/(x1 - x2);
                double b = (y1- m*x1);
                for(int i = xRC + 1; i <= xRF; i++){
                    yR = (int)(m*(i) + b);
                    FiguraPontos.desenharPonto(g, i, yR, "", 10);
                } 
                */
                
            }
            //FiguraPontos.desenharPontosAleatorios(g, 200, 10);
        
        
    }

    // Capturando os Eventos com o mouse
    /**
     * Method mousePressed
     *
     * @param e MouseEvent - click do mouse
     */
    public void mousePressed(MouseEvent e) { 
        Graphics g = getGraphics();
        if(tipo == TiposPrimitivos.PONTO){
            xMouse = e.getX();
            yMouse = e.getY();
            paint(g);
            nPto++;
        }
        if(tipo == TiposPrimitivos.RETA){
            if(primeiraVez){
                x1 = (int)e.getX();
                y1 = (int)e.getY();
                primeiraVez = false;
            }
            else{
                x2 = (int)e.getX();
                y2 = (int)e.getY();
                primeiraVez = true;
                paint(g);
            }
        }
    }     

    public void mouseReleased(MouseEvent e) { 
    }           

    public void mouseClicked(MouseEvent e) {
        this.msg.setText("CLICOU: " + e.getButton());
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    /**
     * mouseMoved evento de movimentação do mouse. Mostra posicao do mouse no painel
     *
     * @param e A parameter
     */
    public void mouseMoved(MouseEvent e) {
        this.msg.setText("("+e.getX() + ", " + e.getY() + ")");
        //System.out.println("("+e.getX() + ", " + e.getY() + ")");
    }
}
