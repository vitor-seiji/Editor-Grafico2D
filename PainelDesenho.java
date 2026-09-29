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
 * 
 * Herda dee implementa as interfaces e
 * para capturar eventos de mouse, possibilitando o
 * desenho interativo de pontos, retas, círculos, retângulos e triângulos, além
 * do efeito elástico durante a criação das formas.
 * 
 * 
 * @author Ana Paula Barros de Jesus 
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
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
     * Forma geométrica selecionada para operações como espelhamento.
     */
    private Object formaSelecionada = null;

    /**
     * Construtor
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
     */
    public void setTipo(TiposPrimitivos tipo){
        this.tipo = tipo;
        this.estadoClique = 0; // reseta o estado de cliques ao trocar de tipo
        this.formaSelecionada = null;
        repaint();
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
     */
    public TiposPrimitivos getTipo(){
        return this.tipo;
    }

    /**
     * Define a cor atual utilizada para desenhar as novas figuras geométricas.
     */
    public void setCorAtual(Color cor) {
        this.corAtual = cor;
    }

    /**
     * Obtém a cor atualmente configurada para desenho.
     */
    public Color getCorAtual() {
        return this.corAtual;
    }

    /**
     * Define a espessura do traço ou diâmetro para os novos primitivos.
     */
    public void setEspessuraAtual(int esp) {
        this.espessuraAtual = esp;
    }
    
    /**
     * Define o filtro de visibilidade para o redesenho seletivo das figuras e atualiza a tela.
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
     */
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
        } else if (estadoClique == 2 && tipo == TiposPrimitivos.ESPELHAMENTO) {
            RetaGr r = new RetaGr(x1, y1, tempX, tempY, Color.RED, 1);
            r.desenharRetaLib(g);
        }
        
    }

    /**
     * Trata o evento de clique/pressionamento do botão do mouse.
     * Controla os estados de clique para definir os vértices necessários de cada primitiva,
     * instanciando e adicionando a forma geométrica correspondente à lista.
     * 
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
        else if(tipo == TiposPrimitivos.ESPELHAMENTO) {
            if (estadoClique == 0) {
                formaSelecionada = selecionarForma(e.getX(), e.getY());
                if (formaSelecionada != null) {
                    estadoClique = 1;
                    msg.setText("Primitivo selecionado! Clique para o 1º ponto da reta de espelhamento.");
                } else {
                    msg.setText("Nenhum primitivo encontrado. Clique perto de uma forma.");
                }
            } else if (estadoClique == 1) {
                x1 = (int)e.getX();
                y1 = (int)e.getY();
                estadoClique = 2;
                msg.setText("Clique para o 2º ponto da reta de espelhamento.");
            } else {
                x2 = (int)e.getX();
                y2 = (int)e.getY();
                
                // Realiza o espelhamento
                if (formaSelecionada instanceof PontoGr) {
                    PontoGr p = (PontoGr) formaSelecionada;
                    Ponto ref = refletirPonto(p, x1, y1, x2, y2);
                    PontoGr pNovo = new PontoGr((int)ref.getX(), (int)ref.getY(), p.getCorPto(), p.getNomePto(), p.getDiametro());
                    formas.add(pNovo);
                } else if (formaSelecionada instanceof RetaGr) {
                    RetaGr r = (RetaGr) formaSelecionada;
                    Ponto p1Ref = refletirPonto(r.getP1(), x1, y1, x2, y2);
                    Ponto p2Ref = refletirPonto(r.getP2(), x1, y1, x2, y2);
                    RetaGr rNova = new RetaGr((int)p1Ref.getX(), (int)p1Ref.getY(), (int)p2Ref.getX(), (int)p2Ref.getY(), r.getCorReta(), r.getNomeReta(), r.getEspReta());
                    rNova.setTipoRetaGr(r.getTipoRetaGr());
                    formas.add(rNova);
                } else if (formaSelecionada instanceof CirculoGr) {
                    CirculoGr c = (CirculoGr) formaSelecionada;
                    Ponto cRef = refletirPonto(c.getCentro(), x1, y1, x2, y2);
                    Ponto prRef = refletirPonto(c.getPontoRaio(), x1, y1, x2, y2);
                    CirculoGr cNovo = new CirculoGr((int)cRef.getX(), (int)cRef.getY(), (int)prRef.getX(), (int)prRef.getY(), c.getCorCirculo(), "", c.getEspReta());
                    formas.add(cNovo);
                } else if (formaSelecionada instanceof RetanguloGr) {
                    RetanguloGr ret = (RetanguloGr) formaSelecionada;
                    Ponto p1Ref = refletirPonto(ret.getP1(), x1, y1, x2, y2);
                    Ponto p2Ref = refletirPonto(ret.getP2(), x1, y1, x2, y2);
                    RetanguloGr retNovo = new RetanguloGr((int)p1Ref.getX(), (int)p1Ref.getY(), (int)p2Ref.getX(), (int)p2Ref.getY(), ret.getCorRetangulo(), ret.getEspessura());
                    formas.add(retNovo);
                } else if (formaSelecionada instanceof TrianguloGr) {
                    TrianguloGr t = (TrianguloGr) formaSelecionada;
                    Ponto p1Ref = refletirPonto(t.getP1(), x1, y1, x2, y2);
                    Ponto p2Ref = refletirPonto(t.getP2(), x1, y1, x2, y2);
                    Ponto p3Ref = refletirPonto(t.getP3(), x1, y1, x2, y2);
                    TrianguloGr tNovo = new TrianguloGr((int)p1Ref.getX(), (int)p1Ref.getY(), (int)p2Ref.getX(), (int)p2Ref.getY(), (int)p3Ref.getX(), (int)p3Ref.getY(), t.getCorTriangulo(), t.getEspessura());
                    formas.add(tNovo);
                }
                
                formaSelecionada = null;
                estadoClique = 0;
                repaint();
                msg.setText("Espelhamento concluído.");
            }
        }
        else if(tipo == TiposPrimitivos.APAGAR_INDIVIDUAL) {
            Object apagar = selecionarForma(e.getX(), e.getY());
            if (apagar != null) {
                formas.remove(apagar);
                repaint();
                msg.setText("Forma apagada com sucesso.");
            } else {
                msg.setText("Nenhuma forma encontrada no local do clique.");
            }
        }
    }     

    /**
     * Trata o evento de liberação do botão do mouse.
     * 
     * @param e Objeto com os dados do evento.
     */
    public void mouseReleased(MouseEvent e) { }           

    /**
     * Trata o evento de clique completo (pressionar e soltar) do mouse.
     * 
     * @param e Objeto com os dados do evento.
     */
    public void mouseClicked(MouseEvent e) { }

    /**
     * Trata o evento em que o mouse entra na área visível do componente.
     * 
     * @param e Objeto com os dados do evento.
     */
    public void mouseEntered(MouseEvent e) { }

    /**
     * Trata o evento em que o mouse sai da área visível do componente.
     * 
     * @param e Objeto com os dados do evento.
     */
    public void mouseExited(MouseEvent e) { }

    /**
     * Trata o evento de arrastar o mouse com o botão pressionado.
     * Redireciona para para atualizar coordenadas e repintar.
     * 
     * @param e Objeto com a posição atual do mouse.
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
     * @param e Objeto com as coordenadas atuais do cursor.
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

    /**
     * Calcula a distância de um ponto a um segmento de reta.
     * Utilizado para detecção de colisão e seleção de retas ou polígonos.
     * 
     * @param px Coordenada X do ponto a ser testado.
     * @param py Coordenada Y do ponto a ser testado.
     * @param x1 Coordenada X inicial do segmento de reta.
     * @param y1 Coordenada Y inicial do segmento de reta.
     * @param x2 Coordenada X final do segmento de reta.
     * @param y2 Coordenada Y final do segmento de reta.
     * @return A menor distância euclidiana entre o ponto e o segmento de reta.
     */
    private double distanciaPontoSegmento(double px, double py, double x1, double y1, double x2, double y2) {
        double l2 = Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
        if (l2 == 0) return Math.hypot(px - x1, py - y1);
        double t = Math.max(0, Math.min(1, ((px - x1) * (x2 - x1) + (py - y1) * (y2 - y1)) / l2));
        double projX = x1 + t * (x2 - x1);
        double projY = y1 + t * (y2 - y1);
        return Math.hypot(px - projX, py - projY);
    }

    /**
     * Encontra a forma geométrica mais próxima da coordenada fornecida, 
     * iterando por todas as figuras na Estrutura de Dados (ED).
     * 
     * @param x Coordenada X do clique do mouse.
     * @param y Coordenada Y do clique do mouse.
     * @return A forma geométrica (PontoGr, RetaGr, etc.) selecionada ou null se nenhuma for encontrada dentro da tolerância.
     */
    private Object selecionarForma(int x, int y) {
        Object selecionada = null;
        double menorDistancia = 15.0; // Tolerância de 15 pixels

        for (Object f : formas) {//Percorre toda a ED e verifica se está perto do clique
            double dist = Double.MAX_VALUE;
            if (f instanceof PontoGr) {
                PontoGr p = (PontoGr) f;
                dist = Math.hypot(x - p.getX(), y - p.getY());
            } else if (f instanceof RetaGr) {
                RetaGr r = (RetaGr) f;
                dist = distanciaPontoSegmento(x, y, r.getP1().getX(), r.getP1().getY(), r.getP2().getX(), r.getP2().getY());
            } else if (f instanceof CirculoGr) {
                CirculoGr c = (CirculoGr) f;
                dist = Math.abs(Math.hypot(x - c.getCentro().getX(), y - c.getCentro().getY()) - c.getRaio());
            } else if (f instanceof RetanguloGr) {
                RetanguloGr r = (RetanguloGr) f;
                double d1 = distanciaPontoSegmento(x, y, r.getP1().getX(), r.getP1().getY(), r.getP2().getX(), r.getP1().getY());
                double d2 = distanciaPontoSegmento(x, y, r.getP2().getX(), r.getP1().getY(), r.getP2().getX(), r.getP2().getY());
                double d3 = distanciaPontoSegmento(x, y, r.getP2().getX(), r.getP2().getY(), r.getP1().getX(), r.getP2().getY());
                double d4 = distanciaPontoSegmento(x, y, r.getP1().getX(), r.getP2().getY(), r.getP1().getX(), r.getP1().getY());
                dist = Math.min(Math.min(d1, d2), Math.min(d3, d4));
            } else if (f instanceof TrianguloGr) {
                TrianguloGr t = (TrianguloGr) f;
                double d1 = distanciaPontoSegmento(x, y, t.getP1().getX(), t.getP1().getY(), t.getP2().getX(), t.getP2().getY());
                double d2 = distanciaPontoSegmento(x, y, t.getP2().getX(), t.getP2().getY(), t.getP3().getX(), t.getP3().getY());
                double d3 = distanciaPontoSegmento(x, y, t.getP3().getX(), t.getP3().getY(), t.getP1().getX(), t.getP1().getY());
                dist = Math.min(d1, Math.min(d2, d3));
            }

            if (dist < menorDistancia) {
                menorDistancia = dist;
                selecionada = f;
            }
        }
        return selecionada;
    }

    /**
     * Reflete matematicamente um ponto geométrico em relação a uma reta arbitrária delimitada por dois pontos.
     * 
     * @param p Ponto geométrico de origem que será refletido.
     * @param rx1 Coordenada X do primeiro ponto da reta de espelhamento.
     * @param ry1 Coordenada Y do primeiro ponto da reta de espelhamento.
     * @param rx2 Coordenada X do segundo ponto da reta de espelhamento.
     * @param ry2 Coordenada Y do segundo ponto da reta de espelhamento.
     * @return Novo Ponto contendo as coordenadas após a operação de reflexão.
     */
    private Ponto refletirPonto(Ponto p, int rx1, int ry1, int rx2, int ry2) {
        double dx = rx2 - rx1;
        double dy = ry2 - ry1;
        double b = dx * dx + dy * dy;
        if (b == 0) return new Ponto(p.getX(), p.getY()); // Previne divisão por zero se a reta for um ponto
        
        double a = (p.getX() - rx1) * dx + (p.getY() - ry1) * dy;
        double cx = rx1 + a * dx / b; // Ponto mais próximo na reta (projeção)
        double cy = ry1 + a * dy / b;
        
        double refX = 2 * cx - p.getX();
        double refY = 2 * cy - p.getY();
        return new Ponto(refX, refY);
    }
}
