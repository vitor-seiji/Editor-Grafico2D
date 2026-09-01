import circulo.*;
import ponto.*;
import reta.*;
import retangulo.*;
import triangulo.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JLabel;
//import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Painel para desenhar primitivos graficos
 * 
 * @author Ana Paula Barros de Jesus 
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 *
 */
public class PainelDesenho extends JPanel implements MouseListener, MouseMotionListener {
    private static final long serialVersionUID = 1L;
    JLabel msg;
    TiposPrimitivos tipo;
    String tipoReta;
    String LimparTipo;
    // Lista para armazenar as formas graficas (Estrutura de Dados - ED)
    private List<Object> formas = new ArrayList<>();
    
    // Filtro de exibicao ("Todos", "Pontos", "Retas", "Circulos", "Retangulos", "Triangulos", "Nenhum")
    private String filtroVisibilidade = "Todos";
    private String naoVisibilidade = "Todos";

    int xMouse, yMouse;
    int x1, y1, x2, y2, x3, y3;
    
    // Estado para cliques multiplos (usado para Reta, Circulo, Retangulo, Triangulo)
    int estadoClique = 0; 
    
    Color corAtual = Color.BLACK;
    int espessuraAtual = 10;

    /**
     * Construtor para objetos da classe PainelDesenho
     */
    public PainelDesenho(JLabel msg, TiposPrimitivos tipo) {
        this.tipo = tipo;
        this.msg = msg;
        this.setBackground(Color.WHITE);
        this.addMouseListener(this); 
        this.addMouseMotionListener(this);
    }
    
    public void setTipo(TiposPrimitivos tipo){
        this.tipo = tipo;
        this.estadoClique = 0; // reseta o estado de cliques ao trocar de tipo
    }
    
    public void setTipoReta(String tipo){
        this.tipoReta = tipo;
    }
    
    public void setLimparTipo(String tipo){
        this.LimparTipo = tipo;
    }

    public TiposPrimitivos getTipo(){
        return this.tipo;
    }

    public void setCorAtual(Color cor) {
        this.corAtual = cor;
    }

    public Color getCorAtual() {
        return this.corAtual;
    }

    public void setEspessuraAtual(int esp) {
        this.espessuraAtual = esp;
    }
    
    /**
     * Define o filtro de visibilidade para o redesenho
     * @param filtro Filtro de primitivos
     */
    public void setFiltroVisibilidade(String filtro) {
        this.filtroVisibilidade = filtro;
        repaint();
    }
    
    /**
     * Limpa apenas a tela (nao remove da ED)
     */
    public void limparTela(){
        this.filtroVisibilidade = "Nenhum";
        repaint();
    }

    /**
     * paintComponent - metodo para desenhar
     * @param g Contexto grafico
     */
    @Override
    public void paintComponent(Graphics g) {   
        super.paintComponent(g); // Limpa o fundo
        
        if (filtroVisibilidade.equals("Nenhum")){
            return; // Nao desenha nada, apenas limpou a tela
        }
        
        Graphics2D g2 = (Graphics2D) g;
        
        // Percorre a ED e desenha conforme o filtro
        for (Object forma : formas) {
            if (forma instanceof PontoGr && (filtroVisibilidade.equals("Todos") || filtroVisibilidade.equals("Pontos"))) {
                PontoGr p = (PontoGr) forma;
                p.desenharPonto(g);
            }
            else if (forma instanceof RetaGr && (filtroVisibilidade.equals("Todos") || filtroVisibilidade.equals("Retas"))) {
                RetaGr r = (RetaGr) forma;
                if(r.getTipoRetaGr().equals("Pontos")){
                    r.desenharReta(g);
                }
                if(r.getTipoRetaGr().equals("MidPoint")){
                    r.desenharRetaMp(g);
                }
                if(r.getTipoRetaGr().equals("Library")){
                    r.desenharRetaLib(g);
                }
            }
            else if (forma instanceof CirculoGr && (filtroVisibilidade.equals("Todos") || filtroVisibilidade.equals("Circulos"))) {
                CirculoGr c = (CirculoGr) forma;
                c.desenharCirculo(g);
            }
            else if (forma instanceof RetanguloGr && (filtroVisibilidade.equals("Todos") || filtroVisibilidade.equals("Retangulos"))) {
                RetanguloGr ret = (RetanguloGr) forma;
                ret.desenharRetangulo(g);
            }
            else if (forma instanceof TrianguloGr && (filtroVisibilidade.equals("Todos") || filtroVisibilidade.equals("Triangulos"))) {
                TrianguloGr tri = (TrianguloGr) forma;
                tri.desenharTriangulo(g);
            }
        }
    }

    /**
     * Evento click do mouse
     * @param e MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) { 
        Graphics g = getGraphics();
        if(tipo == TiposPrimitivos.PONTO){
            xMouse = e.getX();
            yMouse = e.getY();
            PontoGr p = new PontoGr(xMouse, yMouse, corAtual, "", espessuraAtual);
            formas.add(p);
            p.desenharPonto(g); // Desenha direto na tela sem chamar repaint()
        }
        else if(tipo == TiposPrimitivos.RETA || tipo == TiposPrimitivos.CIRCULO || tipo == TiposPrimitivos.RETANGULO){
            if(estadoClique == 0){
                x1 = (int)e.getX();
                y1 = (int)e.getY();
                estadoClique = 1;
            } else {
                x2 = (int)e.getX();
                y2 = (int)e.getY();
                estadoClique = 0;
                
                if (tipo == TiposPrimitivos.RETA) {
                    RetaGr r = new RetaGr(x1, y1, x2, y2, corAtual, espessuraAtual);
                    if(tipoReta.equals("Pontos")){
                        r.desenharReta(g);
                        r.setTipoRetaGr(tipoReta);
                    }
                    if(tipoReta.equals("MidPoint")){
                        r.desenharRetaMp(g);
                        r.setTipoRetaGr(tipoReta);
                    }
                     if(tipoReta.equals("Library")){
                        r.desenharRetaLib(g);
                        r.setTipoRetaGr(tipoReta);
                    }
                    formas.add(r);
                } else if (tipo == TiposPrimitivos.CIRCULO) {
                    CirculoGr c = new CirculoGr(x1, y1, x2, y2, corAtual, "", espessuraAtual);
                    formas.add(c);
                    c.desenharCirculo(g);
                } else if (tipo == TiposPrimitivos.RETANGULO) {
                    RetanguloGr ret = new RetanguloGr(x1, y1, x2, y2, corAtual, espessuraAtual);
                    formas.add(ret);
                    ret.desenharRetangulo(g);
                }
            }
        }
        else if(tipo == TiposPrimitivos.TRIANGULO) {
            if(estadoClique == 0){
                x1 = (int)e.getX();
                y1 = (int)e.getY();
                estadoClique = 1;
            } else if(estadoClique == 1){
                x2 = (int)e.getX();
                y2 = (int)e.getY();
                estadoClique = 2;
            } else {
                x3 = (int)e.getX();
                y3 = (int)e.getY();
                estadoClique = 0;
                TrianguloGr tri = new TrianguloGr(x1, y1, x2, y2, x3, y3, corAtual, espessuraAtual);
                formas.add(tri);
                tri.desenharTriangulo(g);
            }
        }
    }     

    public void mouseReleased(MouseEvent e) { }           
    public void mouseClicked(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseDragged(MouseEvent e) { }

    /**
     * Mostra posicao do mouse no painel
     * @param e MouseEvent
     */
    public void mouseMoved(MouseEvent e) {
        this.msg.setText("(" + e.getX() + ", " + e.getY() + ")");
    }
}
