package ui;

import model.Funcionario;
import service.Sistema;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * TelaPrincipal.java — janela central do ERP.
 * Menu lateral com navegação entre módulos.
 */
public class TelaPrincipal extends JFrame {

    private final Sistema sistema;
    private final Funcionario usuarioLogado;

    private JPanel painelConteudo;

    // Cores do tema
    static final Color COR_SIDEBAR    = new Color(30, 41, 59);
    static final Color COR_SIDEBAR_BTN = new Color(51, 65, 85);
    static final Color COR_SIDEBAR_HOVER = new Color(99, 102, 241);
    static final Color COR_FUNDO      = new Color(248, 250, 252);
    static final Color COR_PRIMARIA   = new Color(99, 102, 241);
    static final Color COR_SUCESSO    = new Color(34, 197, 94);
    static final Color COR_PERIGO     = new Color(239, 68, 68);
    static final Color COR_TEXTO      = new Color(30, 41, 59);
    static final Color COR_TEXTO_CLARO = new Color(100, 116, 139);

    public TelaPrincipal(Sistema sistema, Funcionario usuarioLogado) {
        this.sistema       = sistema;
        this.usuarioLogado = usuarioLogado;
        configurarJanela();
        construirLayout();
        mostrarPainel(new TelaDashboard(sistema, usuarioLogado));
    }

    private void configurarJanela() {
        setTitle("ERP — Business Manager");
        setSize(1100, 680);
        setMinimumSize(new Dimension(900, 580));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
    }

    private void construirLayout() {
        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(COR_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(210, 0));
        sidebar.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Logo / título
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(COR_SIDEBAR);
        logoPanel.setBorder(new EmptyBorder(24, 20, 24, 20));
        JLabel logo = new JLabel("Business Manager");
        logo.setFont(new Font("SansSerif", Font.BOLD, 16));
        logo.setForeground(Color.WHITE);
        logoPanel.add(logo, BorderLayout.CENTER);
        sidebar.add(logoPanel);

        // Separador
        sidebar.add(separador());

        // Botões de navegação
        sidebar.add(botaoMenu("Dashboard",     () -> mostrarPainel(new TelaDashboard(sistema, usuarioLogado))));
        sidebar.add(botaoMenu("Clientes",       () -> mostrarPainel(new TelaClientes(sistema))));
        sidebar.add(botaoMenu("Funcionários",   () -> mostrarPainel(new TelaFuncionarios(sistema))));
        sidebar.add(botaoMenu("Produtos",       () -> mostrarPainel(new TelaProdutos(sistema))));
        sidebar.add(botaoMenu("Vendas",         () -> mostrarPainel(new TelaVendas(sistema))));
        sidebar.add(botaoMenu("Relatórios",     () -> mostrarPainel(new TelaRelatorios(sistema))));

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(separador());

        // Info usuário logado
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setBackground(COR_SIDEBAR);
        userPanel.setBorder(new EmptyBorder(14, 20, 14, 20));
        JLabel nomeUser = new JLabel(usuarioLogado.getNome());
        nomeUser.setForeground(Color.WHITE);
        nomeUser.setFont(new Font("SansSerif", Font.PLAIN, 12));
        JLabel nivelUser = new JLabel(usuarioLogado.getNivelAcesso());
        nivelUser.setForeground(COR_TEXTO_CLARO);
        nivelUser.setFont(new Font("SansSerif", Font.PLAIN, 10));
        userPanel.add(nomeUser, BorderLayout.CENTER);
        userPanel.add(nivelUser, BorderLayout.SOUTH);
        sidebar.add(userPanel);

        // Botão logout
        JButton btnLogout = new JButton("⏻  Sair");
        btnLogout.setForeground(new Color(248, 113, 113));
        btnLogout.setBackground(COR_SIDEBAR);
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnLogout.setBorder(new EmptyBorder(10, 20, 16, 20));
        btnLogout.addActionListener(e -> {
            sistema.logout();
            dispose();
            SwingUtilities.invokeLater(() -> new TelaLogin(sistema).setVisible(true));
        });
        sidebar.add(btnLogout);

        // ── Área de conteúdo ──────────────────────────────────────────────────
        painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.setBackground(COR_FUNDO);

        add(sidebar, BorderLayout.WEST);
        add(painelConteudo, BorderLayout.CENTER);
    }

    public void mostrarPainel(JPanel painel) {
        painelConteudo.removeAll();
        painelConteudo.add(painel, BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }

    // ── Helpers visuais ───────────────────────────────────────────────────────

    private JButton botaoMenu(String texto, Runnable acao) {
        JButton btn = new JButton(texto);
        btn.setForeground(new Color(203, 213, 225));
        btn.setBackground(COR_SIDEBAR);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(COR_SIDEBAR_HOVER);
                btn.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(COR_SIDEBAR);
                btn.setForeground(new Color(203, 213, 225));
            }
        });
        btn.addActionListener(e -> acao.run());
        return btn;
    }

    private JSeparator separador() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(51, 65, 85));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }

    // ── Fábrica de componentes estáticos (usados pelas subtelas) ─────────────

    public static JLabel labelTitulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.BOLD, 22));
        l.setForeground(COR_TEXTO);
        return l;
    }

    public static JButton botaoPrimario(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(COR_PRIMARIA);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        return btn;
    }

    public static JButton botaoPerigo(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(COR_PERIGO);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        return btn;
    }

    public static JButton botaoSucesso(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(COR_SUCESSO);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        return btn;
    }

    public static JPanel cartao(String titulo) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
            new EmptyBorder(16, 20, 16, 20)
        ));
        if (titulo != null && !titulo.isBlank()) {
            JLabel lbl = new JLabel(titulo);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
            lbl.setForeground(COR_TEXTO_CLARO);
            lbl.setBorder(new EmptyBorder(0, 0, 10, 0));
            p.add(lbl, BorderLayout.NORTH);
        }
        return p;
    }
}