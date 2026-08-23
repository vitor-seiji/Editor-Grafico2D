import circulo.*;
import ponto.*;
import reta.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * TiposPrimitivos - constantes com os tipos de primitivos
 * 
 * @author Julio
 * @version 20260803
 */
class Gui extends JFrame {
    TiposPrimitivos tipo = TiposPrimitivos.NENHUM;

    // mensagens
    private JLabel msg = new JLabel("Msg: ");
    private JButton jbPonto = new JButton("Ponto");
    private JButton jbReta = new JButton("Reta");
    private JButton jbCor = new JButton("Cor");
    private JSpinner jsEspessura = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
    private JButton jbLimpar = new JButton("Limpar");

    // barra de menu
    private JToolBar barraComandos = new JToolBar();

    // Painel de desenho
    private PainelDesenho areaDesenho = new PainelDesenho(msg, tipo);

    /**
     * Gui - define janela de largura e altura
     *
     * @param larg int largura da janela
     * @param alt int altura da janela
     */
    public Gui(int larg, int alt) {
        /**
         * Definicoes de janela
         */
        super("Testa Primitivos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(larg, alt);
        // Melhorias na interface
        barraComandos.setFloatable(false);
        msg.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jbPonto.setFocusPainted(false);
        jbReta.setFocusPainted(false);
        jbCor.setFocusPainted(false);
        jbLimpar.setFocusPainted(false);

        // Adicionando os componentes
        barraComandos.add(jbPonto);
        barraComandos.add(jbReta);
        barraComandos.addSeparator();
        barraComandos.add(new JLabel(" Espessura: "));
        jsEspessura.setMaximumSize(new Dimension(60, 30));
        barraComandos.add(jsEspessura);
        barraComandos.addSeparator();
        barraComandos.add(jbCor);
        barraComandos.add(Box.createHorizontalGlue());
        barraComandos.add(jbLimpar);
        add(barraComandos, BorderLayout.NORTH);                
        add(areaDesenho, BorderLayout.CENTER);                
        add(msg, BorderLayout.SOUTH);

        Eventos eventos = new Eventos();
        jbPonto.addActionListener(eventos);
        jbReta.addActionListener(eventos);
        jbCor.addActionListener(eventos);
        jbLimpar.addActionListener(eventos);
        
        jsEspessura.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                areaDesenho.setEspessuraAtual((Integer) jsEspessura.getValue());
            }
        });
        
        setVisible(true);
    }

    /**
     * Eventos - implementa os eventos da interface ActionListener
     * 
     */
    private class Eventos implements ActionListener{

        TiposPrimitivos tipo = TiposPrimitivos.RETA;

        /**
         * actionPerformed - executa evento do ActionListener
         *
         * @param event A parameter
         */
        public void actionPerformed(ActionEvent event) {            

            if (event.getSource() == jbPonto){
                tipo = TiposPrimitivos.PONTO;
                areaDesenho.setTipo(tipo);
            }     
            if (event.getSource() == jbReta){
                tipo = TiposPrimitivos.RETA;
                areaDesenho.setTipo(tipo);
            }
            if (event.getSource() == jbCor){
                Color cor = JColorChooser.showDialog(Gui.this, "Escolha a cor", areaDesenho.getCorAtual());
                if (cor != null) {
                    areaDesenho.setCorAtual(cor);
                    jbCor.setForeground(cor); // Optional: give visual feedback of chosen color
                }
            }
            if (event.getSource() == jbLimpar){
                tipo = TiposPrimitivos.NENHUM;
                areaDesenho.setTipo(tipo);
                areaDesenho.removeAll();
                repaint(); 
            }
            
            if (tipo == TiposPrimitivos.PONTO) {
                // Enviando a Forma a ser desenhada e a cor da linha
                areaDesenho.setTipo( tipo );
            }
            
            if (tipo == TiposPrimitivos.RETA) {
                areaDesenho.setTipo( tipo );
            }
        }
    } 
}

