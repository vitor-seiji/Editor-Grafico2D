import circulo.*;
import ponto.*;
import reta.*;
import retangulo.*;
import triangulo.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Janela principal da interface gráfica do Editor Gráfico.
 * <p>
 * Configura o menu da aplicação (abrir, salvar e sair), a barra de ferramentas
 * com seleção de primitivos geométricos (ponto, reta, círculo, retângulo, triângulo),
 * controle de espessura, seletor de cores, opções de filtro de exibição e limpeza
 * seletiva de elementos. Também provê rotinas completas de serialização e desserialização
 * das figuras gráficas no formato JSON.
 * </p>
 * 
 * @author Ana Paula Barros de Jesus
 * @author Julie Quaglio da Silva
 * @author Vitor Seiji Colombo Nishida
 * @version 1.0
 */
class Gui extends JFrame {
    /**
     * Tipo de primitivo geométrico atualmente selecionado na interface.
     */
    TiposPrimitivos tipo = TiposPrimitivos.NENHUM;

    /**
     * Rótulo de texto localizado no rodapé da janela para exibir coordenadas e mensagens informativas.
     */
    private JLabel msg = new JLabel("Msg: ");
    
    /**
     * Botão para selecionar a ferramenta de desenho de Ponto.
     */
    private JButton jbPonto = new JButton("Ponto");

    /**
     * Vetor contendo as opções de algoritmos disponíveis para traçado de retas ("Pontos", "MidPoint", "Library").
     */
    private String[] tipoRetas = {"Pontos", "MidPoint", "Library"};

    /**
     * Botão para selecionar a ferramenta de desenho de Reta.
     */
    private JButton jbReta = new JButton("Reta");

    /**
     * Botão para selecionar a ferramenta de desenho de Círculo.
     */
    private JButton jbCirculo = new JButton("Circulo");

    /**
     * Botão para selecionar a ferramenta de desenho de Retângulo.
     */
    private JButton jbRetangulo = new JButton("Retangulo");

    /**
     * Botão para selecionar a ferramenta de desenho de Triângulo.
     */
    private JButton jbTriangulo = new JButton("Triangulo");
    
    /**
     * Botão que abre a caixa de diálogo para escolha da cor de desenho.
     */
    private JButton jbCor = new JButton("Cor");

    /**
     * Controle giratório numérico (spinner) para seleção da espessura do traço (1 a 100).
     */
    private JSpinner jsEspessura = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));

    /**
     * Botão para executar a ação de apagar primitivos do tipo selecionado.
     */
    private JButton jbLimpar = new JButton("Limpar");

    /**
     * Opções de categorias de primitivos para filtragem e remoção.
     */
    private String[] filtros = {"Todos", "Pontos", "Retas", "Circulos", "Retangulos", "Triangulos"};

    /**
     * Caixa de seleção para definir o filtro de visibilidade dos primitivos na tela.
     */
    private JComboBox<String> cbFiltro = new JComboBox<>(filtros);

    /**
     * Caixa de seleção para escolher o algoritmo de traçado de retas.
     */
    private JComboBox<String> cbReta = new JComboBox<>(tipoRetas);

    /**
     * Caixa de seleção para escolher qual categoria de primitivos será apagada ao clicar em Limpar.
     */
    private JComboBox<String> cbLimparTipo = new JComboBox<>(filtros);

    /**
     * Barra de comandos e ferramentas (toolbar) localizada no topo da janela.
     */
    private JToolBar barraComandos = new JToolBar();

    /**
     * Painel de desenho onde os elementos gráficos são renderizados e manipulados interativamente.
     */
    private PainelDesenho areaDesenho = new PainelDesenho(msg, tipo);

    /**
     * Construtor da janela principal da interface gráfica.
     * Configura dimensões, layout, barra de menu (Arquivo), barra de ferramentas com botões
     * de primitivos, seletores de cor, espessura e filtros, e associa os respectivos ouvintes de eventos.
     *
     * @param larg Largura inicial da janela em pixels.
     * @param alt Altura inicial da janela em pixels.
     */
    public Gui(int larg, int alt) {
        super("Editor Grafico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(larg, alt);
        
        //Barra de Menu(Arquivo)
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArquivo = new JMenu("Arquivo");
        menuArquivo.setMnemonic(KeyEvent.VK_A);
        
        JMenuItem miAbrir = new JMenuItem("Abrir JSON...");
        miAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        miAbrir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { abrirFigura(); }
        });
        
        JMenuItem miSalvar = new JMenuItem("Salvar JSON...");
        miSalvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        miSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { salvarFigura(); }
        });
        
        JMenuItem miSair = new JMenuItem("Sair");
        miSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { System.exit(0); }
        });
        
        menuArquivo.add(miAbrir);
        menuArquivo.add(miSalvar);
        menuArquivo.addSeparator();
        menuArquivo.add(miSair);
        menuBar.add(menuArquivo);
        setJMenuBar(menuBar);
        
        // --- Toolbar ---
        barraComandos.setFloatable(false);
        barraComandos.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(4, 4, 4, 4)
        ));
        
        msg.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        msg.setFont(new Font("SansSerif", Font.PLAIN, 12));

        // Tooltips nos botoes
        jbPonto.setToolTipText("Desenhar ponto (1 clique)");
        jbReta.setToolTipText("Desenhar reta (2 cliques)");
        jbCirculo.setToolTipText("Desenhar circulo (2 cliques: centro e borda)");
        jbRetangulo.setToolTipText("Desenhar retangulo (2 cliques: cantos opostos)");
        jbTriangulo.setToolTipText("Desenhar triangulo (3 cliques)");
        jbCor.setToolTipText("Escolher cor de desenho");
        jbLimpar.setToolTipText("Apagar primitivos do tipo selecionado");
        
        jbPonto.setFocusPainted(false);
        jbReta.setFocusPainted(false);
        jbCirculo.setFocusPainted(false);
        jbRetangulo.setFocusPainted(false);
        jbTriangulo.setFocusPainted(false);
        jbCor.setFocusPainted(false);
        jbLimpar.setFocusPainted(false);

        // Adicionando os componentes
        barraComandos.add(new JLabel(" Primitivos: "));
        barraComandos.add(jbPonto);
        barraComandos.add(jbReta);
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
        cbFiltro.setMaximumSize(new Dimension(110, 30));
        barraComandos.add(cbFiltro);
        barraComandos.add(Box.createHorizontalGlue());
        barraComandos.add(new JLabel("Apagar: "));
        cbLimparTipo.setMaximumSize(new Dimension(110, 30));
        barraComandos.add(cbLimparTipo);
        barraComandos.addSeparator(new Dimension(4, 0));
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
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Classe interna ouvinte responsável pelo tratamento de eventos de ação dos botões da interface.
     * Implementa a interface {@link ActionListener}.
     */
    private class Eventos implements ActionListener{
        /**
         * Trata os eventos de clique dos botões da barra de ferramentas,
         * alternando o tipo de primitivo a ser desenhado, exibindo opções de algoritmo para retas,
         * abrindo o seletor de cores ou disparando a limpeza da tela.
         * 
         * @param event Objeto {@link ActionEvent} contendo os detalhes do evento de ação disparado.
         */
        public void actionPerformed(ActionEvent event) {    
            if(event.getSource() != jbReta){
                barraComandos.remove(cbReta);
                barraComandos.revalidate();
                barraComandos.repaint();
            }
            if (event.getSource() == jbPonto){
                areaDesenho.setTipo(TiposPrimitivos.PONTO);
            }     
            else if (event.getSource() == jbReta){
                cbReta.setMaximumSize(new Dimension(100, 30));
                barraComandos.add(cbReta);
                barraComandos.revalidate();
                barraComandos.repaint();
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
                String limparTipo = (String) cbLimparTipo.getSelectedItem();
                areaDesenho.setTipo(TiposPrimitivos.NENHUM);
                if(limparTipo.equals("Todos")){
                    areaDesenho.limparTela();
                }
                else{
                    areaDesenho.setFiltroNaoVisibilidade(limparTipo);
                }
            }
        }
    } 

    /**
     * Abre uma caixa de diálogo para salvar a figura atual em formato JSON,
     * serializando todos os primitivos geométricos desenhados na tela com coordenadas normalizadas.
     */
    private void salvarFigura() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Figura como JSON");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos JSON (*.json)", "json"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".json")) {
                file = new File(file.getParentFile(), file.getName() + ".json");
            }
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(gerarJson());
                JOptionPane.showMessageDialog(this, "Arquivo salvo com sucesso!\n" + file.getName(),
                    "Salvar", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo:\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Formata um objeto {@link Color} na representação de objeto JSON contendo seus canais RGB.
     *
     * @param c Objeto {@link Color} a ser formatado. Se for nulo, utiliza {@link Color#BLACK}.
     * @return Fragmento de texto em formato JSON representando a cor.
     */
    private String formatarCor(Color c) {
        if (c == null) c = Color.BLACK;
        return "\t\t\t\t\"cor\": {\n\t\t\t\t\t\"r\": " + c.getRed() + ",\n\t\t\t\t\t\"b\": " + c.getBlue() + ",\n\t\t\t\t\t\"g\": " + c.getGreen() + "\n\t\t\t\t},\n";
    }

    /**
     * Constrói a estrutura JSON completa representando todas as figuras geométricas desenhadas.
     * As coordenadas dos primitivos são normalizadas em relação às dimensões da área de desenho (0.0 a 1.0).
     *
     * @return String contendo a representação JSON formatada de todos os elementos gráficos.
     */
    private String gerarJson() {
        StringBuilder sb = new StringBuilder();
        double w = areaDesenho.getWidth();
        double h = areaDesenho.getHeight();
        if (w == 0) w = 1;
        if (h == 0) h = 1;
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.000", new java.text.DecimalFormatSymbols(java.util.Locale.US));

        sb.append("{\n\t\"figura\": {\n");

        List<Object> formas = areaDesenho.getFormas();
        
        List<PontoGr> pontos = new ArrayList<>();
        List<RetaGr> retas = new ArrayList<>();
        List<TrianguloGr> triangulos = new ArrayList<>();
        List<RetanguloGr> retangulos = new ArrayList<>();
        List<CirculoGr> circulos = new ArrayList<>();

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

    // =====================================================
    //  ABRIR / LER FIGURA DE JSON
    // =====================================================

    /**
     * Abre uma caixa de diálogo para seleção de um arquivo JSON e carrega
     * as figuras salvas nele para a tela de desenho.
     */
    private void abrirFigura() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Abrir Figura JSON");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos JSON (*.json)", "json"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                String conteudo = lerArquivo(file);
                carregarJson(conteudo);
                areaDesenho.setFiltroVisibilidade("Todos");
                areaDesenho.repaint();
                JOptionPane.showMessageDialog(this, "Figura carregada com sucesso!\n" + file.getName(),
                    "Abrir", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao abrir o arquivo:\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Lê e retorna todo o conteúdo de um arquivo em disco como uma String.
     *
     * @param file Arquivo a ser lido.
     * @return Conteúdo textual do arquivo.
     * @throws IOException Se ocorrer algum erro durante a leitura física do arquivo.
     */
    private String lerArquivo(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                sb.append(linha).append("\n");
            }
        }
        return sb.toString();
    }
    
    /**
     * Processa a cadeia de caracteres em formato JSON, extrai as formas geométricas
     * e adiciona cada primitivo (pontos, retas, triângulos, retângulos e círculos) à estrutura de dados do painel.
     *
     * @param json Texto contendo os dados JSON da figura.
     */
    private void carregarJson(String json) {
        double w = areaDesenho.getWidth();
        double h = areaDesenho.getHeight();
        if (w == 0) w = 1;
        if (h == 0) h = 1;
        
        // Carregar pontos
        List<String> blocosPonto = extrairBlocosArray(json, "\"ponto\"");
        for (String bloco : blocosPonto) {
            double x = extrairDouble(bloco, "\"x\"") * w;
            double y = extrairDouble(bloco, "\"y\"") * h;
            Color cor = extrairCor(bloco);
            int esp = extrairInt(bloco, "\"esp\"");
            PontoGr p = new PontoGr((int)x, (int)y, cor, "", esp);
            areaDesenho.addForma(p);
        }
        
        // Carregar retas
        List<String> blocosReta = extrairBlocosArray(json, "\"reta\"");
        for (String bloco : blocosReta) {
            String blocoP1 = extrairBlocoObjeto(bloco, "\"p1\"");
            String blocoP2 = extrairBlocoObjeto(bloco, "\"p2\"");
            double x1r = extrairDouble(blocoP1, "\"x\"") * w;
            double y1r = extrairDouble(blocoP1, "\"y\"") * h;
            double x2r = extrairDouble(blocoP2, "\"x\"") * w;
            double y2r = extrairDouble(blocoP2, "\"y\"") * h;
            Color cor = extrairCor(bloco);
            int esp = extrairInt(bloco, "\"esp\"");
            RetaGr r = new RetaGr((int)x1r, (int)y1r, (int)x2r, (int)y2r, cor, esp);
            r.setTipoRetaGr("MidPoint");
            areaDesenho.addForma(r);
        }
        
        // Carregar triangulos
        List<String> blocosTriangulo = extrairBlocosArray(json, "\"triangulo\"");
        for (String bloco : blocosTriangulo) {
            String blocoP1 = extrairBlocoObjeto(bloco, "\"p1\"");
            String blocoP2 = extrairBlocoObjeto(bloco, "\"p2\"");
            String blocoP3 = extrairBlocoObjeto(bloco, "\"p3\"");
            double x1t = extrairDouble(blocoP1, "\"x\"") * w;
            double y1t = extrairDouble(blocoP1, "\"y\"") * h;
            double x2t = extrairDouble(blocoP2, "\"x\"") * w;
            double y2t = extrairDouble(blocoP2, "\"y\"") * h;
            double x3t = extrairDouble(blocoP3, "\"x\"") * w;
            double y3t = extrairDouble(blocoP3, "\"y\"") * h;
            Color cor = extrairCor(bloco);
            int esp = extrairInt(bloco, "\"esp\"");
            TrianguloGr tri = new TrianguloGr((int)x1t, (int)y1t, (int)x2t, (int)y2t, (int)x3t, (int)y3t, cor, esp);
            areaDesenho.addForma(tri);
        }
        
        // Carregar retangulos
        List<String> blocosRetangulo = extrairBlocosArray(json, "\"retangulo\"");
        for (String bloco : blocosRetangulo) {
            String blocoP1 = extrairBlocoObjeto(bloco, "\"p1\"");
            String blocoP2 = extrairBlocoObjeto(bloco, "\"p2\"");
            double x1r = extrairDouble(blocoP1, "\"x\"") * w;
            double y1r = extrairDouble(blocoP1, "\"y\"") * h;
            double x2r = extrairDouble(blocoP2, "\"x\"") * w;
            double y2r = extrairDouble(blocoP2, "\"y\"") * h;
            Color cor = extrairCor(bloco);
            int esp = extrairInt(bloco, "\"esp\"");
            RetanguloGr ret = new RetanguloGr((int)x1r, (int)y1r, (int)x2r, (int)y2r, cor, esp);
            areaDesenho.addForma(ret);
        }
        
        // Carregar circulos
        List<String> blocosCirculo = extrairBlocosArray(json, "\"circulo\"");
        for (String bloco : blocosCirculo) {
            String blocoCentro = extrairBlocoObjeto(bloco, "\"centro\"");
            String blocoRaio = extrairBlocoObjeto(bloco, "\"raio\"");
            double cx = extrairDouble(blocoCentro, "\"x\"") * w;
            double cy = extrairDouble(blocoCentro, "\"y\"") * h;
            double rx = extrairDouble(blocoRaio, "\"x\"") * w;
            double ry = extrairDouble(blocoRaio, "\"y\"") * h;
            Color cor = extrairCor(bloco);
            int esp = extrairInt(bloco, "\"esp\"");
            // O raio eh calculado pela distancia entre centro e o ponto do raio
            CirculoGr c = new CirculoGr((int)cx, (int)cy, (int)rx, (int)ry, cor, "", esp);
            areaDesenho.addForma(c);
        }
    }


    // Utilitarios simples para parsing de JSON

    
    /**
     * Extrai todos os blocos {} de um array JSON identificado pela chave.
     *
     * @param json Texto JSON contendo o array a ser analisado.
     * @param chave Nome da propriedade que identifica o array JSON (ex.: "\"ponto\"").
     * @return Lista contendo os blocos de texto delimitados por chaves {} correspondentes a cada elemento.
     */
    private List<String> extrairBlocosArray(String json, String chave) {
        List<String> blocos = new ArrayList<>();
        int idx = json.indexOf(chave);
        if (idx == -1) return blocos;
        
        // Encontrar o '[' apos a chave
        int inicioArray = json.indexOf('[', idx);
        if (inicioArray == -1) return blocos;
        
        // Encontrar o ']' correspondente
        int fimArray = encontrarFechamento(json, inicioArray, '[', ']');
        if (fimArray == -1) return blocos;
        
        String conteudoArray = json.substring(inicioArray + 1, fimArray);
        
        // Extrair cada bloco {}
        int pos = 0;
        while (pos < conteudoArray.length()) {
            int inicioBloco = conteudoArray.indexOf('{', pos);
            if (inicioBloco == -1) break;
            int fimBloco = encontrarFechamento(conteudoArray, inicioBloco, '{', '}');
            if (fimBloco == -1) break;
            blocos.add(conteudoArray.substring(inicioBloco, fimBloco + 1));
            pos = fimBloco + 1;
        }
        return blocos;
    }
    
    /**
     * Extrai um sub-objeto {} identificado por uma chave dentro de um bloco JSON.
     *
     * @param bloco Trecho de texto contendo o objeto JSON.
     * @param chave Nome da chave do sub-objeto a ser recuperado (ex.: "\"p1\"").
     * @return Conteúdo textual do sub-objeto delimitado por {}, ou uma string vazia caso não seja encontrado.
     */
    private String extrairBlocoObjeto(String bloco, String chave) {
        int idx = bloco.indexOf(chave);
        if (idx == -1) return "";
        int inicio = bloco.indexOf('{', idx);
        if (inicio == -1) return "";
        int fim = encontrarFechamento(bloco, inicio, '{', '}');
        if (fim == -1) return "";
        return bloco.substring(inicio, fim + 1);
    }
    
    /**
     * Encontra o índice do caractere de fechamento correspondente ao caractere de abertura,
     * levando em consideração níveis aninhados.
     *
     * @param s String a ser analisada.
     * @param posAbertura Posição na string correspondente ao caractere de abertura inicial.
     * @param abertura Caractere de abertura (ex.: '{' ou '[').
     * @param fechamento Caractere de fechamento correspondente (ex.: '}' ou ']').
     * @return Índice do caractere de fechamento correspondente, ou -1 caso não seja localizado.
     */
    private int encontrarFechamento(String s, int posAbertura, char abertura, char fechamento) {
        int nivel = 0;
        for (int i = posAbertura; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == abertura) nivel++;
            else if (c == fechamento) {
                nivel--;
                if (nivel == 0) return i;
            }
        }
        return -1;
    }
    
    /**
     * Extrai um valor numérico de ponto flutuante (double) de um campo JSON simples ("chave": valor).
     *
     * @param bloco Trecho de texto JSON onde a chave está contida.
     * @param chave Identificador do campo no formato JSON (ex.: "\"x\"").
     * @return Valor em ponto flutuante convertido, ou 0.0 caso o campo não seja encontrado ou contenha valor inválido.
     */
    private double extrairDouble(String bloco, String chave) {
        int idx = bloco.indexOf(chave);
        if (idx == -1) return 0;
        int inicioValor = bloco.indexOf(':', idx) + 1;
        // Ler ate virgula, } ou fim de linha
        StringBuilder sb = new StringBuilder();
        for (int i = inicioValor; i < bloco.length(); i++) {
            char c = bloco.charAt(i);
            if (c == ',' || c == '}' || c == '\n') break;
            if (c != ' ' && c != '\t' && c != '\r') sb.append(c);
        }
        try {
            return Double.parseDouble(sb.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    /**
     * Extrai um valor numérico inteiro (int) de um campo JSON simples ("chave": valor).
     *
     * @param bloco Trecho de texto JSON onde a chave está contida.
     * @param chave Identificador do campo no formato JSON (ex.: "\"esp\"").
     * @return Valor inteiro correspondente, ou 0 caso o campo não seja encontrado ou contenha valor inválido.
     */
    private int extrairInt(String bloco, String chave) {
        return (int) extrairDouble(bloco, chave);
    }
    
    /**
     * Extrai as componentes RGB de um sub-bloco JSON de cor ("cor": { "r": ..., "b": ..., "g": ... })
     * e retorna a instância de {@link Color} correspondente com valores ajustados no intervalo [0, 255].
     *
     * @param bloco Trecho de texto JSON que contém o sub-objeto "cor".
     * @return Instância de {@link Color} com as cores configuradas, ou {@link Color#BLACK} caso o bloco seja vazio.
     */
    private Color extrairCor(String bloco) {
        String blocoCor = extrairBlocoObjeto(bloco, "\"cor\"");
        if (blocoCor.isEmpty()) return Color.BLACK;
        int r = extrairInt(blocoCor, "\"r\"");
        int g = extrairInt(blocoCor, "\"g\"");
        int b = extrairInt(blocoCor, "\"b\"");
        // Garantir valores validos
        r = Math.max(0, Math.min(255, r));
        g = Math.max(0, Math.min(255, g));
        b = Math.max(0, Math.min(255, b));
        return new Color(r, g, b);
    }
}
