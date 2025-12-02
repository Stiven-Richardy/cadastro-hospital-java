/*
IFSP - CAMPUS CUBATÃO
TURMA: ADS 471 - LINGUAGEM DE PROGRAMAÇÃO II
INTEGRANTES:
-> Guilherme Mendes de Sousa
-> Stiven Richardy Silva Rodrigues
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;

public class Form extends JFrame {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=hospital;encrypt=false;trustServerCertificate=true;";
    private static final String USUARIO = "user_java";
    private static final String SENHA = "Senha2025@";

    private JPanel painelPrincipal;
    private JPanel painelSuperior;
    private JPanel painelCentral;
    private JPanel painelInferior;
    private JSeparator separatorSuperior = new JSeparator();
    private JSeparator separatorInferior = new JSeparator();

    private JTextField txtNomePesquisado = new JTextField(20);
    private JTextField txtNome = new JTextField(20);
    private JTextField txtIdade = new JTextField(20);
    private JTextField txtPeso = new JTextField(20);
    private JTextField txtAltura = new JTextField(20);

    private JButton btnPesquisar = new JButton("Pesquisar");
    private JButton btnCadastrar = new JButton("Cadastrar");
    private JButton btnExibir = new JButton("Exibir Dados");
    private JButton btnLimpar = new JButton("Limpar");
    private JButton btnSair = new JButton("Sair");

    public Form() {
        setTitle("P2 - LPR2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        painelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelSuperior.setBorder(new EmptyBorder(10, 10, 0, 0));
        painelSuperior.add(new JLabel("Nome:"));
        painelSuperior.add(txtNomePesquisado);
        painelSuperior.add(btnPesquisar);

        painelCentral = new JPanel(new GridLayout(4, 1, 10, 10));
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

        btnLimpar.addActionListener(e -> limparTela());
        btnSair.addActionListener(e -> System.exit(0));

        add(painelPrincipal, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    public void limparTela() {
        txtNomePesquisado.setText("");
        txtNome.setText("");
        txtIdade.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
    }

    public static void main(String[] args) {
        String sqlCreateCargos = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='pessoas')"
                + "CREATE TABLE pessoas ("
                + "  id VARCHAR(40) PRIMARY KEY"
                + "  nome VARCHAR(50)"
                + "  idade INT"
                + "  peso FLOAT"
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