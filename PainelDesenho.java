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
 * Painel de desenho responsável pela renderização e interação com primitivos gráficos.
 * <p>
 * Herda de {@link JPanel} e implementa as interfaces {@link MouseListener} e
 * {@link MouseMotionListener} para capturar eventos de mouse, possibilitando o
 * desenho interativo de pontos, retas, círculos, retângulos e triângulos, além
 * do efeito elástico (rubber-banding) durante a criação das formas.
 * </p>
 * 
 * @author Ana Paula Barros de Jesus 
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 * @version 1.0
 */
public class PainelDesenho extends JPanel implements MouseListener, MouseMotionListener {
    /**
     * Identificador de versão para a serialização da classe.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Rótulo de texto da interface utilizado para exibir mensagens de status e coordenadas do mouse.
     */
    JLabel msg;

    /**
     * Tipo de primitivo geométrico atualmente selecionado para desenho.
     */
    TiposPrimitivos tipo;

    /**
     * Algoritmo selecionado para o desenho de retas ("Pontos", "MidPoint" ou "Library").
     */
    String tipoReta;
    
    /**
     * Lista utilizada como estrutura de dados (ED) para armazenar as formas gráficas desenhadas.
     */
    private List<Object> formas = new ArrayList<>();
    
    /**
     * Filtro de exibição ativo ("Todos", "Pontos", "Retas", "Circulos", "Retangulos", "Triangulos" ou "Nenhum").
     */
    private String filtroVisibilidade = "Todos";

    /**
     * Coordenadas X e Y da posição do mouse no momento do clique (utilizadas no desenho de pontos).
     */
    int xMouse, yMouse;

    /**
     * Coordenadas dos vértices utilizadas na construção de primitivos geométricos com múltiplos cliques.
     */
    int x1, y1, x2, y2, x3, y3;
    
    /**
     * Contador de estado para controlar a sequência de cliques necessários na definição de cada primitivo.
     */
    int estadoClique = 0; 
    
    /**
     * Cor selecionada atualmente para desenhar os primitivos gráficos.
     */
    Color corAtual = Color.BLACK;

    /**
     * Espessura (em pixels) selecionada para o traço ou diâmetro dos primitivos desenhados.
     */
    int espessuraAtual = 10;

    /**
     * Construtor da classe PainelDesenho.
     * Inicializa o painel com cor de fundo branca, associa o rótulo de mensagens,
     * define o primitivo inicial e registra os ouvintes de eventos do mouse.
     * 
     * @param msg Rótulo de texto ({@link JLabel}) para exibição de mensagens e coordenadas.
     * @param tipo Tipo primitivo inicial selecionado.
     */
    public PainelDesenho(JLabel msg, TiposPrimitivos tipo) {
        this.tipo = tipo;
        this.msg = msg;
        this.setBackground(Color.WHITE);
        this.addMouseListener(this); 
        this.addMouseMotionListener(this);
    }
    
    /**
     * Altera o tipo de primitivo geométrico ativo para desenho e reinicia o estado de cliques.
     * 
     * @param tipo Novo tipo primitivo a ser desenhado.
     */
    public void setTipo(TiposPrimitivos tipo){
        this.tipo = tipo;
        this.estadoClique = 0; // reseta o estado de cliques ao trocar de tipo
    }
    
    /**
     * Define o algoritmo utilizado para traçar retas.
     * 
     * @param tipo Nome do algoritmo ("Pontos", "MidPoint" ou "Library").
     */
    public void setTipoReta(String tipo){
        this.tipoReta = tipo;
    }
    
    /**
     * Obtém o tipo de primitivo geométrico atualmente ativo.
     * 
     * @return O tipo de primitivo atual ({@link TiposPrimitivos}).
     */
    public TiposPrimitivos getTipo(){
        return this.tipo;
    }

    /**
     * Define a cor atual utilizada para desenhar as novas figuras geométricas.
     * 
     * @param cor Nova cor a ser aplicada.
     */
    public void setCorAtual(Color cor) {
        this.corAtual = cor;
    }

    /**
     * Obtém a cor atualmente configurada para desenho.
     * 
     * @return A cor atual de desenho ({@link Color}).
     */
    public Color getCorAtual() {
        return this.corAtual;
    }

    /**
     * Define a espessura do traço ou diâmetro para os novos primitivos.
     * 
     * @param esp Valor inteiro representando a espessura em pixels.
     */
    public void setEspessuraAtual(int esp) {
        this.espessuraAtual = esp;
    }
    
    /**
     * Define o filtro de visibilidade para o redesenho seletivo das figuras e atualiza a tela.
     * 
     * @param filtro Nome da categoria de primitivos a ser exibida.
     */
    public void setFiltroVisibilidade(String filtro) {
        this.filtroVisibilidade = filtro;
        repaint();
    }
    
    /**
     * Remove da estrutura de dados as formas que correspondem à categoria informada e atualiza a tela.
     * 
     * @param filtro Nome da categoria de primitivos a ser removida da tela.
     */
    public void setFiltroNaoVisibilidade(String filtro) {
        if (filtro.equals("Todos")) {
            formas.clear();
        } else {
            java.util.Iterator<Object> it = formas.iterator();
            while (it.hasNext()) {
                Object f = it.next();
                if (filtro.equals("Pontos") && f instanceof PontoGr) {
                    it.remove();
                } else if (filtro.equals("Retas") && f instanceof RetaGr) {
                    it.remove();
                } else if (filtro.equals("Circulos") && f instanceof CirculoGr) {
                    it.remove();
                } else if (filtro.equals("Retangulos") && f instanceof RetanguloGr) {
                    it.remove();
                } else if (filtro.equals("Triangulos") && f instanceof TrianguloGr) {
                    it.remove();
                }
            }
        }
        repaint();
    }
    
    /**
     * Limpa a tela e esvazia a estrutura de dados com todas as formas armazenadas.
     */
    public void limparTela(){
        formas.clear();
        repaint();
    }

    /**
     * Sobrescreve a renderização do componente para desenhar as formas que atendem
     * ao filtro de visibilidade ativo e renderizar o elástico temporário durante a criação.
     * 
     * @param g Contexto gráfico utilizado para o desenho.
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
                if(r.getTipoRetaGr() != null && r.getTipoRetaGr().equals("Pontos")){
                    r.desenharReta(g);
                }
                else if(r.getTipoRetaGr() != null && r.getTipoRetaGr().equals("MidPoint")){
                    r.desenharRetaMp(g);
                }
                else {
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
        
        // Desenho dos elásticos
        if (estadoClique == 1) {
            if (tipo == TiposPrimitivos.RETA) {
                RetaGr r = new RetaGr(x1, y1, tempX, tempY, corAtual, espessuraAtual);
                if(tipoReta != null && tipoReta.equals("Pontos")){
                    r.desenharReta(g);
                } else if(tipoReta != null && tipoReta.equals("MidPoint")){
                    r.desenharRetaMp(g);
                } else {
                    r.desenharRetaLib(g);
                }
            } else if (tipo == TiposPrimitivos.CIRCULO) {
                CirculoGr c = new CirculoGr(x1, y1, tempX, tempY, corAtual, "", espessuraAtual);
                c.desenharCirculo(g);
            } else if (tipo == TiposPrimitivos.RETANGULO) {
                RetanguloGr ret = new RetanguloGr(x1, y1, tempX, tempY, corAtual, espessuraAtual);
                ret.desenharRetangulo(g);
            }
        } else if (estadoClique == 2 && tipo == TiposPrimitivos.TRIANGULO) {
            TrianguloGr tri = new TrianguloGr(x1, y1, x2, y2, tempX, tempY, corAtual, espessuraAtual);
            tri.desenharTriangulo(g);
        } else if (estadoClique == 1 && tipo == TiposPrimitivos.TRIANGULO) {
            RetaGr r = new RetaGr(x1, y1, tempX, tempY, corAtual, espessuraAtual);
            r.desenharRetaLib(g);
        }
        
    }

    /**
     * Trata o evento de clique/pressionamento do botão do mouse.
     * Controla os estados de clique para definir os vértices necessários de cada primitiva,
     * instanciando e adicionando a forma geométrica correspondente à lista.
     * 
     * @param e Objeto {@link MouseEvent} com informações do evento do mouse.
     */
    @Override
    public void mousePressed(MouseEvent e) { 
        Graphics g = getGraphics();
        if(tipo == TiposPrimitivos.PONTO){
            xMouse = e.getX();
            yMouse = e.getY();
            PontoGr p = new PontoGr(xMouse, yMouse, corAtual, "", espessuraAtual);
            formas.add(p);
            repaint();
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
                    if(tipoReta != null) {
                        r.setTipoRetaGr(tipoReta);
                    } else {
                        r.setTipoRetaGr("Library");
                    }
                    formas.add(r);
                } else if (tipo == TiposPrimitivos.CIRCULO) {
                    CirculoGr c = new CirculoGr(x1, y1, x2, y2, corAtual, "", espessuraAtual);
                    formas.add(c);
                } else if (tipo == TiposPrimitivos.RETANGULO) {
                    RetanguloGr ret = new RetanguloGr(x1, y1, x2, y2, corAtual, espessuraAtual);
                    formas.add(ret);
                }
                repaint();
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
                repaint();
            }
        }
    }     

    /**
     * Trata o evento de liberação do botão do mouse.
     * 
     * @param e Objeto {@link MouseEvent} com os dados do evento.
     */
    public void mouseReleased(MouseEvent e) { }           

    /**
     * Trata o evento de clique completo (pressionar e soltar) do mouse.
     * 
     * @param e Objeto {@link MouseEvent} com os dados do evento.
     */
    public void mouseClicked(MouseEvent e) { }

    /**
     * Trata o evento em que o mouse entra na área visível do componente.
     * 
     * @param e Objeto {@link MouseEvent} com os dados do evento.
     */
    public void mouseEntered(MouseEvent e) { }

    /**
     * Trata o evento em que o mouse sai da área visível do componente.
     * 
     * @param e Objeto {@link MouseEvent} com os dados do evento.
     */
    public void mouseExited(MouseEvent e) { }

    /**
     * Trata o evento de arrastar o mouse com o botão pressionado.
     * Redireciona para {@link #mouseMoved(MouseEvent)} para atualizar coordenadas e repintar.
     * 
     * @param e Objeto {@link MouseEvent} com a posição atual do mouse.
     */
    public void mouseDragged(MouseEvent e) { 
        mouseMoved(e);
    }

    /**
     * Coordenadas temporárias X e Y do cursor do mouse, atualizadas durante o movimento para o efeito elástico.
     */
    int tempX, tempY;

    /**
     * Trata o movimento do cursor do mouse sobre o painel.
     * Exibe as coordenadas atuais no rótulo de mensagens e, se houver um desenho em andamento,
     * atualiza o traçado elástico e repinta a tela.
     * 
     * @param e Objeto {@link MouseEvent} com as coordenadas atuais do cursor.
     */
    public void mouseMoved(MouseEvent e) {
        this.msg.setText("(" + e.getX() + ", " + e.getY() + ")");
        if (estadoClique > 0) {
            tempX = e.getX();
            tempY = e.getY();
            repaint();
        }
    }
    
    /**
     * Retorna a lista contendo as formas geométricas armazenadas no painel.
     * 
     * @return Lista com os objetos das formas geométricas.
     */
    public List<Object> getFormas() {
        return formas;
    }
    
    /**
     * Adiciona uma forma gráfica diretamente à estrutura de dados do painel
     * e restaura o filtro de visibilidade para "Todos".
     * 
     * @param forma Objeto da forma geométrica (ex.: PontoGr, RetaGr, CirculoGr, RetanguloGr, TrianguloGr).
     */
    public void addForma(Object forma) {
        formas.add(forma);
        this.filtroVisibilidade = "Todos";
    }
}
