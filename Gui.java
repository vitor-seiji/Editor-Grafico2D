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
    private JButton jbSalvar = new JButton("Salvar");

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
        jbSalvar.setFocusPainted(false);

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
        cbLimparTipo.setMaximumSize(new Dimension(100, 30));
        barraComandos.add(cbLimparTipo);
        barraComandos.addSeparator();
        barraComandos.add(jbSalvar);
        
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
        jbSalvar.addActionListener(eventos);
        
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
                String limparTipo = "Todos";
                limparTipo = (String) cbLimparTipo.getSelectedItem();
                areaDesenho.setTipo(TiposPrimitivos.NENHUM);
                if(limparTipo.equals("Todos")){
                    areaDesenho.limparTela();
                }
                else{
                    areaDesenho.setFiltroNaoVisibilidade(limparTipo);
                    areaDesenho.repaint();
                }
            }
            else if (event.getSource() == jbSalvar) {
                salvarFigura();
            }
        }
    } 

    private void salvarFigura() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".json")) {
                file = new java.io.File(file.getParentFile(), file.getName() + ".json");
            }
            try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
                writer.write(gerarJson());
                JOptionPane.showMessageDialog(this, "Arquivo salvo com sucesso!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String formatarCor(Color c) {
        if (c == null) c = Color.BLACK;
        return "\t\t\t\t\"cor\": {\n\t\t\t\t\t\"r\": " + c.getRed() + ",\n\t\t\t\t\t\"b\": " + c.getBlue() + ",\n\t\t\t\t\t\"g\": " + c.getGreen() + "\n\t\t\t\t},\n";
    }

    private String gerarJson() {
        StringBuilder sb = new StringBuilder();
        double w = areaDesenho.getWidth();
        double h = areaDesenho.getHeight();
        if (w == 0) w = 1;
        if (h == 0) h = 1;
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.000", new java.text.DecimalFormatSymbols(java.util.Locale.US));

        sb.append("{\n\t\"figura\": {\n");

        java.util.List<Object> formas = areaDesenho.getFormas();
        
        java.util.List<PontoGr> pontos = new java.util.ArrayList<>();
        java.util.List<RetaGr> retas = new java.util.ArrayList<>();
        java.util.List<TrianguloGr> triangulos = new java.util.ArrayList<>();
        java.util.List<RetanguloGr> retangulos = new java.util.ArrayList<>();
        java.util.List<CirculoGr> circulos = new java.util.ArrayList<>();

        for (Object f : formas) {
            if (f instanceof PontoGr) pontos.add((PontoGr) f);
            else if (f instanceof RetaGr) retas.add((RetaGr) f);
            else if (f instanceof TrianguloGr) triangulos.add((TrianguloGr) f);
            else if (f instanceof RetanguloGr) retangulos.add((RetanguloGr) f);
            else if (f instanceof CirculoGr) circulos.add((CirculoGr) f);
        }

        boolean firstElement = true;

        if (!pontos.isEmpty()) {
            sb.append("\t\t\"ponto\": [\n");
            for (int i = 0; i < pontos.size(); i++) {
                PontoGr p = pontos.get(i);
                sb.append("\t\t\t{\n");
                sb.append("\t\t\t\t\"x\": ").append(df.format(p.getX() / w)).append(",\n");
                sb.append("\t\t\t\t\"y\": ").append(df.format(p.getY() / h)).append(",\n");
                sb.append(formatarCor(p.getCorPto()));
                sb.append("\t\t\t\t\"esp\": ").append(p.getDiametro()).append(",\n");
                sb.append("\t\t\t\t\"id\": \"ponto_").append(i+1).append("\"\n");
                sb.append("\t\t\t}").append(i < pontos.size() - 1 ? "," : "").append("\n");
            }
            sb.append("\t\t]");
            firstElement = false;
        }

        if (!retas.isEmpty()) {
            if (!firstElement) sb.append(",\n");
            sb.append("\t\t\"reta\": [\n");
            for (int i = 0; i < retas.size(); i++) {
                RetaGr r = retas.get(i);
                sb.append("\t\t\t{\n");
                sb.append("\t\t\t\t\"p1\": {\n");
                sb.append("\t\t\t\t\t\"x\": ").append(df.format(r.getP1().getX() / w)).append(",\n");
                sb.append("\t\t\t\t\t\"y\": ").append(df.format(r.getP1().getY() / h)).append("\n");
                sb.append("\t\t\t\t},\n");
                sb.append("\t\t\t\t\"p2\": {\n");
                sb.append("\t\t\t\t\t\"x\": ").append(df.format(r.getP2().getX() / w)).append(",\n");
                sb.append("\t\t\t\t\t\"y\": ").append(df.format(r.getP2().getY() / h)).append("\n");
                sb.append("\t\t\t\t},\n");
                sb.append(formatarCor(r.getCorReta()));
                sb.append("\t\t\t\t\"esp\": ").append(r.getEspReta()).append(",\n");
                sb.append("\t\t\t\t\"id\": \"reta_").append(i+1).append("\"\n");
                sb.append("\t\t\t}").append(i < retas.size() - 1 ? "," : "").append("\n");
            }
            sb.append("\t\t]");
            firstElement = false;
        }

        if (!triangulos.isEmpty()) {
            if (!firstElement) sb.append(",\n");
            sb.append("\t\t\"triangulo\": [\n");
            for (int i = 0; i < triangulos.size(); i++) {
                TrianguloGr t = triangulos.get(i);
                sb.append("\t\t\t{\n");
                sb.append("\t\t\t\t\"p1\": {\n");
                sb.append("\t\t\t\t\t\"x\": ").append(df.format(t.getP1().getX() / w)).append(",\n");
                sb.append("\t\t\t\t\t\"y\": ").append(df.format(t.getP1().getY() / h)).append("\n");
                sb.append("\t\t\t\t},\n");
                sb.append("\t\t\t\t\"p2\": {\n");
                sb.append("\t\t\t\t\t\"x\": ").append(df.format(t.getP2().getX() / w)).append(",\n");
                sb.append("\t\t\t\t\t\"y\": ").append(df.format(t.getP2().getY() / h)).append("\n");
                sb.append("\t\t\t\t},\n");
                sb.append("\t\t\t\t\"p3\": {\n");
                sb.append("\t\t\t\t\t\"x\": ").append(df.format(t.getP3().getX() / w)).append(",\n");
                sb.append("\t\t\t\t\t\"y\": ").append(df.format(t.getP3().getY() / h)).append("\n");
                sb.append("\t\t\t\t},\n");
                sb.append(formatarCor(t.getCorTriangulo()));
                sb.append("\t\t\t\t\"esp\": ").append(t.getEspessura()).append(",\n");
                sb.append("\t\t\t\t\"id\": \"triangulo_").append(i+1).append("\"\n");
                sb.append("\t\t\t}").append(i < triangulos.size() - 1 ? "," : "").append("\n");
            }
            sb.append("\t\t]");
            firstElement = false;
        }

        if (!retangulos.isEmpty()) {
            if (!firstElement) sb.append(",\n");
            sb.append("\t\t\"retangulo\": [\n");
            for (int i = 0; i < retangulos.size(); i++) {
                RetanguloGr r = retangulos.get(i);
                sb.append("\t\t\t{\n");
                sb.append("\t\t\t\t\"p1\": {\n");
                sb.append("\t\t\t\t\t\"x\": ").append(df.format(r.getP1().getX() / w)).append(",\n");
                sb.append("\t\t\t\t\t\"y\": ").append(df.format(r.getP1().getY() / h)).append("\n");
                sb.append("\t\t\t\t},\n");
                sb.append("\t\t\t\t\"p2\": {\n");
                sb.append("\t\t\t\t\t\"x\": ").append(df.format(r.getP2().getX() / w)).append(",\n");
                sb.append("\t\t\t\t\t\"y\": ").append(df.format(r.getP2().getY() / h)).append("\n");
                sb.append("\t\t\t\t},\n");
                sb.append(formatarCor(r.getCorRetangulo()));
                sb.append("\t\t\t\t\"esp\": ").append(r.getEspessura()).append(",\n");
                sb.append("\t\t\t\t\"id\": \"retangulo_").append(i+1).append("\"\n");
                sb.append("\t\t\t}").append(i < retangulos.size() - 1 ? "," : "").append("\n");
            }
            sb.append("\t\t]");
            firstElement = false;
        }

        if (!circulos.isEmpty()) {
            if (!firstElement) sb.append(",\n");
            sb.append("\t\t\"circulo\": [\n");
            for (int i = 0; i < circulos.size(); i++) {
                CirculoGr c = circulos.get(i);
                sb.append("\t\t\t{\n");
                sb.append("\t\t\t\t\"centro\": {\n");
                sb.append("\t\t\t\t\t\"x\": ").append(df.format(c.getCentro().getX() / w)).append(",\n");
                sb.append("\t\t\t\t\t\"y\": ").append(df.format(c.getCentro().getY() / h)).append("\n");
                sb.append("\t\t\t\t},\n");
                sb.append("\t\t\t\t\"raio\": {\n");
                sb.append("\t\t\t\t\t\"x\": ").append(df.format((c.getCentro().getX() + c.getRaio()) / w)).append(",\n");
                sb.append("\t\t\t\t\t\"y\": ").append(df.format(c.getCentro().getY() / h)).append("\n");
                sb.append("\t\t\t\t},\n");
                sb.append(formatarCor(c.getCorCirculo()));
                sb.append("\t\t\t\t\"esp\": ").append(c.getEspReta()).append(",\n");
                sb.append("\t\t\t\t\"id\": \"circulo_").append(i+1).append("\"\n");
                sb.append("\t\t\t}").append(i < circulos.size() - 1 ? "," : "").append("\n");
            }
            sb.append("\t\t]");
        }

        sb.append("\n\t}\n}");
        return sb.toString();
    }
}
