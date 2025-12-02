/*
IFSP - CAMPUS CUBATÃO
TURMA: ADS 471 - LINGUAGEM DE PROGRAMAÇÃO II
INTEGRANTES:
-> Guilherme Mendes de Sousa
-> Stiven Richardy Silva Rodrigues
*/

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class Form extends JFrame {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=hospital;encrypt=false;trustServerCertificate=true;";
    private static final String USUARIO = "user_java";
    private static final String SENHA = "Senha2025@";

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    private JPanel painelPrincipal;
    private JPanel painelSuperior;
    private JPanel painelCentral;
    private JPanel painelInferior;
    private JSeparator separatorSuperior = new JSeparator();
    private JSeparator separatorInferior = new JSeparator();

    private JTextField txtNomePesquisado = new JTextField(20);
    private JTextField txtNome = new JTextField(20);
    private JTextField txtIdade;
    private JTextField txtPeso;
    private JTextField txtAltura;

    private JButton btnPesquisar = new JButton("Pesquisar");
    private JButton btnCadastrar = new JButton("Cadastrar");
    private JButton btnExibir = new JButton("Exibir Dados");
    private JButton btnLimpar = new JButton("Limpar");
    private JButton btnSair = new JButton("Sair");

    public Form() {
        setTitle("P2 - LPR2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        txtIdade = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.GRAY);
                String unidade = "Anos";
                int larguraTexto = g.getFontMetrics().stringWidth(unidade);
                g.drawString(unidade, getWidth() - larguraTexto - 5,
                        (getHeight() + g.getFontMetrics().getAscent()) / 2 - 2);
            }
        };
        txtIdade.setBorder(
                BorderFactory.createCompoundBorder(txtIdade.getBorder(), BorderFactory.createEmptyBorder(0, 0, 0, 40)));

        txtPeso = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.GRAY);
                String unidade = "Kg";
                int larguraTexto = g.getFontMetrics().stringWidth(unidade);
                g.drawString(unidade, getWidth() - larguraTexto - 5,
                        (getHeight() + g.getFontMetrics().getAscent()) / 2 - 2);
            }
        };
        txtPeso.setBorder(
                BorderFactory.createCompoundBorder(txtPeso.getBorder(), BorderFactory.createEmptyBorder(0, 0, 0, 30)));

        try {
            MaskFormatter mascaraAltura = new MaskFormatter("#.##");
            mascaraAltura.setPlaceholderCharacter('_');
            txtAltura = new JFormattedTextField(mascaraAltura) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(Color.GRAY);
                    String unidade = "m";
                    int larguraTexto = g.getFontMetrics().stringWidth(unidade);
                    g.drawString(unidade, getWidth() - larguraTexto - 5,
                            (getHeight() + g.getFontMetrics().getAscent()) / 2 - 2);
                }
            };
            txtAltura.setColumns(20);
            txtAltura.setBorder(BorderFactory.createCompoundBorder(UIManager.getBorder("TextField.border"),
                    BorderFactory.createEmptyBorder(0, 0, 0, 25)));
        } catch (ParseException e) {
            e.printStackTrace();
            txtAltura = new JTextField(20);
        }

        painelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelSuperior.setBorder(new EmptyBorder(10, 10, 0, 0));
        painelSuperior.add(new JLabel("Nome:"));
        painelSuperior.add(txtNomePesquisado);
        painelSuperior.add(btnPesquisar);

        painelCentral = new JPanel(new GridLayout(4, 2, 10, 10));
        painelCentral.setBorder(new EmptyBorder(10, 10, 10, 10));

        painelCentral.add(new JLabel("Nome:"));
        painelCentral.add(txtNome);

        painelCentral.add(new JLabel("Idade:"));
        painelCentral.add(txtIdade);

        painelCentral.add(new JLabel("Peso:"));
        painelCentral.add(txtPeso);

        painelCentral.add(new JLabel("Altura:"));
        painelCentral.add(txtAltura);

        painelInferior = new JPanel(new GridLayout(1, 4, 10, 10));
        painelInferior.setBorder(new EmptyBorder(10, 10, 10, 10));

        painelInferior.add(btnCadastrar);
        painelInferior.add(btnExibir);
        painelInferior.add(btnLimpar);
        painelInferior.add(btnSair);

        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        separatorSuperior.setForeground(Color.BLACK);
        separatorInferior.setForeground(Color.BLACK);

        painelPrincipal.add(painelSuperior);
        painelPrincipal.add(separatorSuperior);
        painelPrincipal.add(painelCentral);
        painelPrincipal.add(separatorInferior);
        painelPrincipal.add(painelInferior);

        btnPesquisar.addActionListener(e -> pesquisarPessoa());
        btnCadastrar.addActionListener(e -> cadastrarPessoa());
        btnLimpar.addActionListener(e -> limparTela());
        btnSair.addActionListener(e -> System.exit(0));

        add(painelPrincipal, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void pesquisarPessoa() {
        String nome = txtNomePesquisado.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, digite um nome para pesquisar.",
                    "Campo Vazio",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "SELECT nome, idade, altura, peso FROM pessoas WHERE nome LIKE ?";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome + "%");
            rs = ps.executeQuery();
            if (rs.next()) {
                atualizarCampos();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Nenhum registro encontrado para: " + nome,
                        "Aviso",
                        JOptionPane.INFORMATION_MESSAGE);
                limparTela();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro SQL: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarCampos() {
        try {
            String nome = rs.getString("nome");
            int idade = rs.getInt("idade");
            float peso = rs.getFloat("peso");
            float altura = rs.getFloat("altura");

            txtNome.setText(nome);
            txtIdade.setText(String.valueOf(idade));
            txtPeso.setText(String.valueOf(peso));
            txtAltura.setText(String.format("%.2f", altura).replace(",", "."));

            btnCadastrar.setEnabled(false);
            txtNome.setEnabled(false);
            txtIdade.setEnabled(false);
            txtPeso.setEnabled(false);
            txtAltura.setEnabled(false);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao ler dados: " + ex.getMessage());
        }
    }

    private void cadastrarPessoa() {
        String nome = txtNome.getText().trim();
        String idadeStr = txtIdade.getText().trim();
        String pesoStr = txtPeso.getText().trim();
        String alturaStr = txtAltura.getText().trim();

        try {
            int idade = Integer.parseInt(idadeStr);
            float peso = Float.parseFloat(pesoStr);
            float altura = Float.parseFloat(alturaStr);

            Pessoa pessoa = new Pessoa(nome, idade, peso, altura);
            String sql = "INSERT INTO pessoas (id, nome, idade, peso, altura) VALUES (?, ?, ?, ?, ?)";
            conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            ps = conn.prepareStatement(sql);
            ps.setString(1, pessoa.getUuid());
            ps.setString(2, pessoa.getNome());
            ps.setInt(3, pessoa.getIdade());
            ps.setFloat(4, pessoa.getPeso());
            ps.setFloat(5, pessoa.getAltura());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Pessoa cadastrada  com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            limparTela();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro: Formato numérico inválido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro SQL: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limparTela() {
        txtNomePesquisado.setText("");
        txtNome.setText("");
        txtIdade.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
        btnCadastrar.setEnabled(true);
        txtNome.setEnabled(true);
        txtIdade.setEnabled(true);
        txtPeso.setEnabled(true);
        txtAltura.setEnabled(true);
    }

    public static void main(String[] args) {
        String sqlCreateCargos = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='pessoas')"
                + "CREATE TABLE pessoas ("
                + "  id VARCHAR(40) PRIMARY KEY,"
                + "  nome VARCHAR(50),"
                + "  idade INT,"
                + "  peso FLOAT,"
                + "  altura FLOAT"
                + ")";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
                Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCreateCargos);
            System.out.println("Conexão com o Banco de Dados estabelecida com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro na conexão com o Banco de Dados: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> new Form().setVisible(true));
    }
}