package ponto;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Representacao grafica de um ponto matematico para renderizacao em tela.
 * Estende {@link Ponto}, agregando atributos visuais como cor de exibicao,
 * rotulo/nome textual, cor do rotulo e diametro em pixels.
 * Fornece construtores flexiveis, getters, setters e metodo para desenhar o ponto
 * em um contexto grafico {@link Graphics}.
 *
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 */
public class PontoGr extends Ponto {

    /**
     * Cor utilizada para o desenho do ponto.
     */
    Color corPto = Color.BLACK;

    /**
     * Nome ou rotulo textual associado ao ponto.
     */
    String nomePto = "";

    /**
     * Cor utilizada para desenhar o texto do nome do ponto.
     */
    Color corNomePto  = Color.BLACK;

    /**
     * Diametro do ponto em pixels (tamanho do circulo). Padrao e 1.
     */
    int diametro = 1;
 
    /**
     * Constroi um ponto grafico com coordenadas inteiras, cor preta e sem rotulo.
     * 
     * @param x Coordenada horizontal (eixo X) do ponto
     * @param y Coordenada vertical (eixo Y) do ponto
     */
    public PontoGr(int x, int y){
        super((double)x, (double)y);
        setCorPto(Color.black);     
        setCorNomePto(Color.black);     
        setNomePto("");     
    }

    /**
     * Constroi um ponto grafico com coordenadas inteiras e cor especificada.
     *
     * @param x Coordenada horizontal (eixo X) do ponto
     * @param y Coordenada vertical (eixo Y) do ponto
     * @param cor Cor do ponto
     */
    public PontoGr(int x, int y, Color cor){
        super((double)x, (double)y);
        setCorPto(cor);     
        setCorNomePto(Color.black);     
        setNomePto("");     
    }

    /**
     * Constroi um ponto grafico na posicao especificada com cor e diametro.
     * 
     * @param x Coordenada horizontal (eixo X)
     * @param y Coordenada vertical (eixo Y)
     * @param corPonto Cor do ponto a ser construido
     * @param diametro Diametro do ponto em pixels
     */
    public PontoGr(int x, int y, Color corPonto, int diametro){
        this(x, y, corPonto);
        setDiametro(diametro);
    }

    /**
     * Constroi um ponto grafico na posicao especificada com cor, nome e diametro.
     * 
     * @param x Coordenada horizontal (eixo X)
     * @param y Coordenada vertical (eixo Y)
     * @param corPonto Cor do ponto a ser construido
     * @param nomePonto Nome ou rotulo do ponto
     * @param diametro Diametro do ponto em pixels
     */
    public PontoGr(int x, int y, Color corPonto, String nomePonto, int diametro){
        this(x, y, corPonto, diametro);
        setNomePto(nomePonto);
    }

    /**
     * Constroi um ponto grafico com coordenadas, cor e nome especificados.
     *
     * @param x Coordenada horizontal (eixo X) do ponto
     * @param y Coordenada vertical (eixo Y) do ponto
     * @param cor Cor do ponto
     * @param str Nome ou rotulo textual do ponto
     */
    public PontoGr(int x, int y, Color cor, String str){
        super((double)x, (double)y);
        setCorPto(cor);     
        setCorNomePto(Color.black);     
        setNomePto(str);     
    }

    /**
     * Constroi um ponto grafico a partir de outro ponto grafico existente, definindo uma nova cor.
     *
     * @param p2d Ponto grafico de origem
     * @param cor Nova cor do ponto
     */
    public PontoGr(PontoGr p2d, Color cor){
        super(p2d);     
        setCorPto(cor);     
        setCorNomePto(Color.black);     
        setNomePto("");     
    }

    /**
     * Constroi um ponto grafico padrao posicionado na origem (0, 0), com cor preta, sem nome e diametro 1.
     */
    public PontoGr(){
        super();     
        setCorPto(Color.black);     
        setCorNomePto(Color.black);     
        setNomePto("");     
    }

    /**
     * Retorna a cor do ponto.
     *
     * @return Cor do ponto
     */
    public Color getCorPto() {
        return corPto;
    }

    /**
     * Define a cor do ponto.
     *
     * @param corPto Nova cor do ponto
     */
    public void setCorPto(Color corPto) {
        this.corPto = corPto;
    }

    /**
     * Retorna o nome ou rotulo do ponto.
     *
     * @return Nome do ponto
     */
    public String getNomePto() {
        return nomePto;
    }

    /**
     * Define o nome ou rotulo do ponto.
     *
     * @param nomePto Novo nome do ponto
     */
    public void setNomePto(String nomePto) {
        this.nomePto = nomePto;
    }

    /**
     * Retorna a cor utilizada para renderizar o texto do nome do ponto.
     *
     * @return Cor do nome do ponto
     */
    public Color getCorNomePto() {
        return corNomePto;
    }

    /**
     * Define a cor utilizada para renderizar o texto do nome do ponto.
     *
     * @param corNomePto Nova cor do nome do ponto
     */
    public void setCorNomePto(Color corNomePto) {
        this.corNomePto = corNomePto;
    }

    /**
     * Retorna o diametro do ponto em pixels.
     *
     * @return Diametro do ponto
     */
    public int getDiametro() {
        return diametro;
    }

    /**
     * Define o diametro do ponto em pixels.
     *
     * @param diametro Novo diametro do ponto
     */
    public void setDiametro(int diametro) {
        this.diametro = diametro;
    }

    /**
     * Desenha o ponto grafico na tela como um circulo preenchido e exibe seu rotulo textual.
     * 
     * @param g Contexto grafico utilizado para a renderizacao
     */
    public void desenharPonto(Graphics g){
        // desenha ponto como um oval
        g.setColor(getCorPto());
        g.fillOval((int)getX() -(getDiametro()/2), (int)getY() - (getDiametro()/2), getDiametro(), getDiametro());

        // desenha nome do ponto
        g.setColor(getCorNomePto());
        g.drawString(getNomePto(), (int)getX() + getDiametro(), (int)getY());
    }
    
}


