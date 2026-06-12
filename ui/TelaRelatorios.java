package ui;

import model.NotaFiscal;
import model.Vendas;
import service.Sistema;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaRelatorios extends JPanel {

    private final Sistema sistema;

    public TelaRelatorios(Sistema sistema) {
        this.sistema = sistema;
        setBackground(TelaPrincipal.COR_FUNDO);
        setLayout(new BorderLayout());
        construir();
    }

    private void construir() {
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(TelaPrincipal.COR_FUNDO);
        cabecalho.setBorder(new EmptyBorder(28, 32, 16, 32));
        cabecalho.add(TelaPrincipal.labelTitulo("Relatórios"), BorderLayout.WEST);
        add(cabecalho, BorderLayout.NORTH);

        // ── Botões de relatório ───────────────────────────────────────────────
        JPanel grid = new JPanel(new GridLayout(2, 2, 16, 16));
        grid.setBackground(TelaPrincipal.COR_FUNDO);
        grid.setBorder(new EmptyBorder(0, 32, 32, 32));

        grid.add(cardRelatorio("Relatório de Vendas",
            "Visualize todas as vendas realizadas com totais.",
            () -> mostrarRelatorioVendas()));

        grid.add(cardRelatorio("Estoque de Produtos",
            "Veja os produtos com seus níveis de estoque.",
            () -> mostrarEstoque()));

        grid.add(cardRelatorio("Clientes Cadastrados",
            "Lista completa de clientes do sistema.",
            () -> mostrarClientes()));

        grid.add(cardRelatorio("Funcionários",
            "Lista de funcionários e cargos.",
            () -> mostrarFuncionarios()));

        add(grid, BorderLayout.CENTER);
    }

    private JPanel cardRelatorio(String titulo, String descricao, Runnable acao) {
        JPanel card = TelaPrincipal.cartao(null);
        card.setLayout(new BorderLayout(0, 8));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lTitulo = new JLabel(titulo);
        lTitulo.setFont(new Font("SansSerif", Font.BOLD, 15));
        lTitulo.setForeground(TelaPrincipal.COR_TEXTO);

        JLabel lDesc = new JLabel("<html><p style='width:200px'>" + descricao + "</p></html>");
        lDesc.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lDesc.setForeground(TelaPrincipal.COR_TEXTO_CLARO);

        JButton btnVer = TelaPrincipal.botaoPrimario("Ver Relatório");
        btnVer.addActionListener(e -> acao.run());

        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 4));
        textos.setBackground(Color.WHITE);
        textos.add(lTitulo);
        textos.add(lDesc);

        card.add(textos, BorderLayout.CENTER);
        card.add(btnVer, BorderLayout.SOUTH);
        return card;
    }

    private void mostrarRelatorioVendas() {
        if (sistema.getVendas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma venda registrada.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        double totalGeral = 0;
        for (Vendas v : sistema.getVendas()) {
            sb.append(v.gerarRelatorio()).append("\n");
            totalGeral += v.calcularTotal();
        }
        sb.append(String.format("═══ TOTAL GERAL: R$ %.2f ═══%n", totalGeral));
        exibirTexto("Relatório de Vendas", sb.toString());
    }

    private void mostrarEstoque() {
        if (sistema.getProdutos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum produto cadastrado.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-25s %10s %8s %s%n",
            "Código", "Nome", "Preço", "Estoque", "Categoria"));
        sb.append("─".repeat(70)).append("\n");
        for (model.Produtos p : sistema.getProdutos()) {
            sb.append(String.format("%-10s %-25s R$%8.2f %8d %s%n",
                p.getCodigo(), p.getNome(), p.getPreco(),
                p.getEstoque(), p.getCategoria()));
        }
        exibirTexto("Estoque de Produtos", sb.toString());
    }

    private void mostrarClientes() {
        if (sistema.getClientes().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente cadastrado.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (model.Clientes c : sistema.getClientes()) {
            sb.append("Nome: ").append(c.getNome())
              .append(" | CPF: ").append(c.getCpf())
              .append(" | Email: ").append(c.getEmail()).append("\n");
        }
        exibirTexto("Clientes Cadastrados", sb.toString());
    }

    private void mostrarFuncionarios() {
        if (sistema.getFuncionarios().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum funcionário cadastrado.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (model.Funcionario f : sistema.getFuncionarios()) {
            sb.append("[").append(f.getMatricula()).append("] ")
              .append(f.getNome()).append(" — ").append(f.getCargo())
              .append(" | ").append(f.getNivelAcesso()).append("\n");
        }
        exibirTexto("Funcionários", sb.toString());
    }

    private void exibirTexto(String titulo, String conteudo) {
        JTextArea area = new JTextArea(conteudo);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setBorder(new EmptyBorder(8, 8, 8, 8));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(680, 420));
        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.PLAIN_MESSAGE);
    }
}