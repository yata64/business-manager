package ui;

import model.Funcionario;
import service.Sistema;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaDashboard extends JPanel {

    private final Sistema sistema;
    private final Funcionario usuario;

    public TelaDashboard(Sistema sistema, Funcionario usuario) {
        this.sistema  = sistema;
        this.usuario  = usuario;
        setBackground(TelaPrincipal.COR_FUNDO);
        setLayout(new BorderLayout());
        construir();
    }

    private void construir() {
        // ── Cabeçalho ─────────────────────────────────────────────────────────
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(TelaPrincipal.COR_FUNDO);
        cabecalho.setBorder(new EmptyBorder(28, 32, 16, 32));

        JLabel titulo = TelaPrincipal.labelTitulo("Dashboard");
        JLabel saudacao = new JLabel("Bem-vindo(a), " + usuario.getNome() + "!");
        saudacao.setFont(new Font("SansSerif", Font.PLAIN, 13));
        saudacao.setForeground(TelaPrincipal.COR_TEXTO_CLARO);

        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 2));
        textos.setBackground(TelaPrincipal.COR_FUNDO);
        textos.add(titulo);
        textos.add(saudacao);
        cabecalho.add(textos, BorderLayout.WEST);

        add(cabecalho, BorderLayout.NORTH);

        // ── Cards de resumo ───────────────────────────────────────────────────
        JPanel gridCards = new JPanel(new GridLayout(1, 4, 16, 0));
        gridCards.setBackground(TelaPrincipal.COR_FUNDO);
        gridCards.setBorder(new EmptyBorder(0, 32, 24, 32));

        gridCards.add(card("Clientes",     String.valueOf(sistema.getClientes().size()),    new Color(99, 102, 241)));
        gridCards.add(card("Funcionários", String.valueOf(sistema.getFuncionarios().size()), new Color(34, 197, 94)));
        gridCards.add(card("Produtos",     String.valueOf(sistema.getProdutos().size()),    new Color(251, 146, 60)));
        gridCards.add(card("Vendas",       String.valueOf(sistema.getVendas().size()),      new Color(236, 72, 153)));

        add(gridCards, BorderLayout.CENTER);

        // ── Info de acesso ────────────────────────────────────────────────────
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rodape.setBackground(TelaPrincipal.COR_FUNDO);
        rodape.setBorder(new EmptyBorder(0, 32, 24, 32));

        JPanel infoAcesso = TelaPrincipal.cartao(null);
        infoAcesso.setLayout(new GridLayout(3, 1, 0, 4));
        infoAcesso.setPreferredSize(new Dimension(360, 90));
        infoAcesso.add(label("Usuário: " + usuario.getNome(), Font.BOLD));
        infoAcesso.add(label("Nível de Acesso: " + usuario.getNivelAcesso(), Font.PLAIN));
        infoAcesso.add(label("Matrícula: " + usuario.getMatricula(), Font.PLAIN));
        rodape.add(infoAcesso);

        add(rodape, BorderLayout.SOUTH);
    }

    private JPanel card(String titulo, String valor, Color cor) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
            new EmptyBorder(20, 24, 20, 24)
        ));

        JLabel lTitulo = new JLabel(titulo);
        lTitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lTitulo.setForeground(TelaPrincipal.COR_TEXTO_CLARO);

        JLabel lValor = new JLabel(valor);
        lValor.setFont(new Font("SansSerif", Font.BOLD, 36));
        lValor.setForeground(cor);

        p.add(lTitulo, BorderLayout.NORTH);
        p.add(lValor, BorderLayout.CENTER);
        return p;
    }

    private JLabel label(String texto, int estilo) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", estilo, 12));
        l.setForeground(TelaPrincipal.COR_TEXTO);
        return l;
    }
}