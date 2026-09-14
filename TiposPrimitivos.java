/**
 * Enumeração que define os tipos de primitivos geométricos suportados pelo editor gráfico.
 * Permite identificar qual forma geométrica está ativa para desenho ou filtragem.
 * 
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 * @version 1.0
 */
public enum TiposPrimitivos {
    /**
     * Primitiva geométrica de um Ponto.
     */
    PONTO,

    /**
     * Primitiva geométrica de uma Reta (segmento de reta).
     */
    RETA,

    /**
     * Primitiva geométrica de um Círculo.
     */
    CIRCULO,

    /**
     * Primitiva geométrica de um Retângulo.
     */
    RETANGULO,

    /**
     * Primitiva geométrica de um Triângulo.
     */
    TRIANGULO,

    /**
     * Indica a ausência de seleção de primitiva geométrica ou estado neutro.
     */
    NENHUM
}
