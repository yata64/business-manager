package ui;

import exceptions.AutenticacaoException;
import model.Funcionario;
import service.Sistema;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    private final Sistema sistema;
    private JTextField     campoMatricula;
    private JPasswordField campoSenha;
    private JLabel         labelStatus;

    public TelaLogin(Sistema sistema) {
        this.sistema = sistema;
        configurarJanela();
        construirLayout();
    }

    private void configurarJanela() {
        setTitle("Business Manager — Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void construirLayout() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(7, 8, 7, 8);

        // Título
        JLabel titulo = new JLabel("Business Manager", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(new Color(99, 102, 241));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        painel.add(titulo, g);

        JLabel sub = new JLabel("Sistema de Gestão Empresarial", SwingConstants.CENTER);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(new Color(100, 116, 139));
        g.gridy = 1;
        painel.add(sub, g);

        // Campos
        g.gridwidth = 1; g.gridy = 2; g.gridx = 0;
        painel.add(rotulo("Matrícula:"), g);
        campoMatricula = campo();
        g.gridx = 1; painel.add(campoMatricula, g);

        g.gridy = 3; g.gridx = 0;
        painel.add(rotulo("Senha:"), g);
        campoSenha = new JPasswordField(15);
        estilizarCampo(campoSenha);
        g.gridx = 1; painel.add(campoSenha, g);

        // Botão
        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setBackground(new Color(99, 102, 241));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        g.gridy = 4; g.gridx = 0; g.gridwidth = 2;
        painel.add(btnEntrar, g);

        // Status
        labelStatus = new JLabel(" ", SwingConstants.CENTER);
        labelStatus.setFont(new Font("SansSerif", Font.PLAIN, 11));
        g.gridy = 5;
        painel.add(labelStatus, g);

        add(painel);

        btnEntrar.addActionListener(e -> tentarLogin());
        campoSenha.addActionListener(e -> tentarLogin());
    }

    private void tentarLogin() {
        String matricula = campoMatricula.getText().trim();
        String senha     = new String(campoSenha.getPassword()).trim();

        if (matricula.isBlank() || senha.isBlank()) {
            labelStatus.setForeground(Color.RED);
            labelStatus.setText("Preencha matrícula e senha.");
            return;
        }

        try {
            Funcionario logado = sistema.login(matricula, senha);
            labelStatus.setForeground(new Color(22, 163, 74));
            labelStatus.setText("Bem-vindo(a), " + logado.getNome() + "!");

            // Abre TelaPrincipal após 800ms
            Timer t = new Timer(800, e -> {
                dispose();
                new TelaPrincipal(sistema, logado).setVisible(true);
            });
            t.setRepeats(false);
            t.start();

        } catch (AutenticacaoException ex) {
            labelStatus.setForeground(Color.RED);
            labelStatus.setText("Matrícula ou senha inválidos.");
            campoSenha.setText("");
            campoMatricula.requestFocus();
        }
    }

    private JTextField campo() {
        JTextField f = new JTextField(15);
        estilizarCampo(f);
        return f;
    }

    private void estilizarCampo(JTextField f) {
        f.setFont(new Font("SansSerif", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225)),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
    }

    private JLabel rotulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        l.setForeground(new Color(30, 41, 59));
        return l;
    }

    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        sistema.carregarDados();

        if (sistema.getFuncionarios().isEmpty()) {
            sistema.cadastrarFuncionario(new Funcionario(
                "Administrador", "000.000.000-00",
                "F001", "Gerente", 5000.0, "admin123", "ADMIN"));
            System.out.println("[Login] Usuário de teste → Matrícula: F001 | Senha: admin123");
        }

        SwingUtilities.invokeLater(() -> new TelaLogin(sistema).setVisible(true));
    }
}