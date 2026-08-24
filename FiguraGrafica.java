import java.awt.Graphics;

/**
 * Interface FiguraGrafica para todos os primitivos desenhaveis.
 * 
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 */
public interface FiguraGrafica {
    /**
     * Desenha a figura utilizando o contexto grafico fornecido.
     * @param g Contexto grafico
     */
    void desenhar(Graphics g);
}
