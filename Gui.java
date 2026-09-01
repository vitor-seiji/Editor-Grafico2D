import circulo.*;
import ponto.*;
import reta.*;
import retangulo.*;
import triangulo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Interface Grafica do Editor
 * 
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 */
class Gui extends JFrame {
    TiposPrimitivos tipo = TiposPrimitivos.NENHUM;

    // mensagens
    private JLabel msg = new JLabel("Msg: ");
    
    // Botoes de primitivos
    private JButton jbPonto = new JButton("Ponto");
    private String[] tipoRetas = {"Pontos", "MidPoint", "Library"};
    private JButton jbReta = new JButton("Reta");
    private JButton jbCirculo = new JButton("Circulo");
    private JButton jbRetangulo = new JButton("Retangulo");
    private JButton jbTriangulo = new JButton("Triangulo");
    
    private JButton jbCor = new JButton("Cor");
    private JSpinner jsEspessura = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
    private JButton jbLimpar = new JButton("Limpar");

    // Combo de filtro
    private String[] filtros = {"Todos", "Pontos", "Retas", "Circulos", "Retangulos", "Triangulos"};
    private JComboBox<String> cbFiltro = new JComboBox<>(filtros);
    private JComboBox<String> cbReta = new JComboBox<>(tipoRetas);
    private JComboBox<String> cbLimparTipo = new JComboBox<>(filtros);

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
        super("Testa Primitivos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(larg, alt);
        
        // Melhorias na interface
        barraComandos.setFloatable(false);
        msg.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jbPonto.setFocusPainted(false);
        jbReta.setFocusPainted(false);
        jbCirculo.setFocusPainted(false);
        jbRetangulo.setFocusPainted(false);
        jbTriangulo.setFocusPainted(false);
        jbCor.setFocusPainted(false);
        jbLimpar.setFocusPainted(false);

        // Adicionando os componentes
        barraComandos.add(jbPonto);
        barraComandos.add(jbReta);
        //barraComandos.add(cbReta);
        barraComandos.add(jbCirculo);
        barraComandos.add(jbRetangulo);
        barraComandos.add(jbTriangulo);
        barraComandos.addSeparator();
        barraComandos.add(new JLabel(" Espessura: "));
        jsEspessura.setMaximumSize(new Dimension(60, 30));
        barraComandos.add(jsEspessura);
        barraComandos.addSeparator();
        barraComandos.add(jbCor);
        barraComandos.addSeparator();
        barraComandos.add(new JLabel(" Mostrar: "));
        cbFiltro.setMaximumSize(new Dimension(100, 30));
        barraComandos.add(cbFiltro);
        barraComandos.add(Box.createHorizontalGlue());
        barraComandos.add(jbLimpar);
        
        add(barraComandos, BorderLayout.NORTH);                
        add(areaDesenho, BorderLayout.CENTER);                
        add(msg, BorderLayout.SOUTH);

        Eventos eventos = new Eventos();
        jbPonto.addActionListener(eventos);
        jbReta.addActionListener(eventos);
        jbCirculo.addActionListener(eventos);
        jbRetangulo.addActionListener(eventos);
        jbTriangulo.addActionListener(eventos);
        jbCor.addActionListener(eventos);
        jbLimpar.addActionListener(eventos);
        
        cbFiltro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selecionado = (String) cbFiltro.getSelectedItem();
                areaDesenho.setFiltroVisibilidade(selecionado);
            }
        });
        
        jsEspessura.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                areaDesenho.setEspessuraAtual((Integer) jsEspessura.getValue());
            }
        });
        
        setVisible(true);
    }

    /**
     * Eventos - implementa os eventos da interface ActionListener
     */
    private class Eventos implements ActionListener{
        /**
         * actionPerformed - executa evento do ActionListener
         * @param event ActionEvent
         */
        public void actionPerformed(ActionEvent event) {    
            if(event.getSource() != jbReta){
                barraComandos.remove(cbReta);
            }
            if (event.getSource() == jbPonto){
                areaDesenho.setTipo(TiposPrimitivos.PONTO);
            }     
            else if (event.getSource() == jbReta){
                cbReta.setMaximumSize(new Dimension(100, 30));
                barraComandos.add(cbReta);
                String tipoReta = (String) cbReta.getSelectedItem();
                areaDesenho.setTipo(TiposPrimitivos.RETA);
                areaDesenho.setTipoReta(tipoReta);
            }
            else if (event.getSource() == jbCirculo){
                areaDesenho.setTipo(TiposPrimitivos.CIRCULO);
            }
            else if (event.getSource() == jbRetangulo){
                areaDesenho.setTipo(TiposPrimitivos.RETANGULO);
            }
            else if (event.getSource() == jbTriangulo){
                areaDesenho.setTipo(TiposPrimitivos.TRIANGULO);
            }
            else if (event.getSource() == jbCor){
                Color cor = JColorChooser.showDialog(Gui.this, "Escolha a cor", areaDesenho.getCorAtual());
                if (cor != null) {
                    areaDesenho.setCorAtual(cor);
                    jbCor.setForeground(cor);
                }
            }
            else if (event.getSource() == jbLimpar){
                cbLimparTipo.setMaximumSize(new Dimension(100, 30));
                barraComandos.add(cbLimparTipo);
                String limparTipo = (String) cbLimparTipo.getSelectedItem();
                areaDesenho.setTipo(TiposPrimitivos.NENHUM);
                areaDesenho.setLimparTipo(limparTipo);
                areaDesenho.limparTela();
            }
        }
    } 
}
