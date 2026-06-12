package ui;

import model.Funcionario;
import service.Sistema;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaFuncionarios extends JPanel {

    private final Sistema sistema;
    private DefaultTableModel modeloTabela;
    private JTable tabela;

    public TelaFuncionarios(Sistema sistema) {
        this.sistema = sistema;
        setBackground(TelaPrincipal.COR_FUNDO);
        setLayout(new BorderLayout());
        construir();
    }

    private void construir() {
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(TelaPrincipal.COR_FUNDO);
        cabecalho.setBorder(new EmptyBorder(28, 32, 16, 32));
        cabecalho.add(TelaPrincipal.labelTitulo("Funcionários"), BorderLayout.WEST);
        JButton btnNovo = TelaPrincipal.botaoPrimario("+ Novo Funcionário");
        btnNovo.addActionListener(e -> abrirFormulario(-1));
        cabecalho.add(btnNovo, BorderLayout.EAST);
        add(cabecalho, BorderLayout.NORTH);

        String[] colunas = {"Matrícula", "Nome", "CPF", "Cargo", "Salário", "Nível"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        estilizarTabela(tabela);
        atualizarTabela();

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        // ── Barra de ações ────────────────────────────────────────────────────
        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        acoes.setBackground(Color.WHITE);
        acoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(226, 232, 240)));

        JButton btnEditar      = TelaPrincipal.botaoPrimario("✏ Editar");
        JButton btnExcluir     = TelaPrincipal.botaoPerigo("🗑 Excluir");
        JButton btnAlterarSenha = new JButton("Alterar Senha");
        estilizarBtnSecundario(btnAlterarSenha);

        btnEditar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row < 0) { avisar("Selecione um funcionário para editar."); return; }
            abrirFormulario(row);
        });

        btnExcluir.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row < 0) { avisar("Selecione um funcionário para excluir."); return; }
            Funcionario f = sistema.getFuncionarios().get(row);
            if (f.equals(sistema.getFuncionarioLogado())) {
                avisar("Não é possível excluir o usuário logado.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                "Excluir funcionário \"" + f.getNome() + "\"?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                sistema.getFuncionarios().remove(row);
                atualizarTabela();
            }
        });

        btnAlterarSenha.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row < 0) { avisar("Selecione um funcionário."); return; }
            Funcionario f = sistema.getFuncionarios().get(row);
            String novaSenha = JOptionPane.showInputDialog(this,
                "Nova senha para " + f.getNome() + ":", "Alterar Senha", JOptionPane.PLAIN_MESSAGE);
            if (novaSenha != null && !novaSenha.isBlank()) {
                f.setSenha(novaSenha.trim());
                JOptionPane.showMessageDialog(this, "Senha alterada com sucesso.");
            }
        });

        acoes.add(btnEditar);
        acoes.add(btnExcluir);
        acoes.add(btnAlterarSenha);

        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(new EmptyBorder(0, 32, 32, 32));
        painelTabela.setBackground(TelaPrincipal.COR_FUNDO);
        painelTabela.add(scroll, BorderLayout.CENTER);
        painelTabela.add(acoes, BorderLayout.SOUTH);
        add(painelTabela, BorderLayout.CENTER);
    }

    private void abrirFormulario(int rowEditar) {
        boolean editando = rowEditar >= 0;
        Funcionario existente = editando ? sistema.getFuncionarios().get(rowEditar) : null;

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                editando ? "Editar Funcionário" : "Novo Funcionário", true);
        dialog.setSize(420, 420);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(7, 2, 8, 12));
        form.setBorder(new EmptyBorder(24, 24, 16, 24));
        form.setBackground(Color.WHITE);

        JTextField fNome      = campo(editando ? existente.getNome()       : "");
        JTextField fCpf       = campo(editando ? existente.getCpf()        : "");
        JTextField fMatricula = campo(editando ? existente.getMatricula()  : "");
        JTextField fCargo     = campo(editando ? existente.getCargo()      : "");
        JTextField fSalario   = campo(editando ? String.valueOf(existente.getSalario()) : "");
        JTextField fSenha     = campo(editando ? existente.getSenha()      : "");
        String[] niveis = {"VENDEDOR", "ADMIN"};
        JComboBox<String> fNivel = new JComboBox<>(niveis);
        if (editando) fNivel.setSelectedItem(existente.getNivelAcesso());

        form.add(rotulo("Nome:"));       form.add(fNome);
        form.add(rotulo("CPF:"));        form.add(fCpf);
        form.add(rotulo("Matrícula:"));  form.add(fMatricula);
        form.add(rotulo("Cargo:"));      form.add(fCargo);
        form.add(rotulo("Salário:"));    form.add(fSalario);
        form.add(rotulo("Senha:"));      form.add(fSenha);
        form.add(rotulo("Nível:"));      form.add(fNivel);

        JButton btnSalvar   = TelaPrincipal.botaoPrimario(editando ? "Salvar Alterações" : "Cadastrar");
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dialog.dispose());

        btnSalvar.addActionListener(e -> {
            if (fNome.getText().isBlank() || fMatricula.getText().isBlank()) {
                JOptionPane.showMessageDialog(dialog, "Nome e Matrícula são obrigatórios.");
                return;
            }
            try {
                double salario = Double.parseDouble(fSalario.getText().trim());
                if (editando) {
                    existente.setNome(fNome.getText().trim());
                    existente.setCargo(fCargo.getText().trim());
                    existente.setSalario(salario);
                    existente.setSenha(fSenha.getText().trim());
                    existente.setNivelAcesso((String) fNivel.getSelectedItem());
                } else {
                    sistema.cadastrarFuncionario(new Funcionario(
                        fNome.getText().trim(), fCpf.getText().trim(),
                        fMatricula.getText().trim(), fCargo.getText().trim(),
                        salario, fSenha.getText().trim(),
                        (String) fNivel.getSelectedItem()
                    ));
                }
                atualizarTabela();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Salário deve ser um número.");
            }
        });

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        rodape.setBackground(Color.WHITE);
        rodape.add(btnCancelar);
        rodape.add(btnSalvar);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(rodape, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Funcionario f : sistema.getFuncionarios()) {
            modeloTabela.addRow(new Object[]{
                f.getMatricula(), f.getNome(), f.getCpf(),
                f.getCargo(), String.format("R$ %.2f", f.getSalario()), f.getNivelAcesso()
            });
        }
    }

    private void estilizarTabela(JTable t) {
        t.setRowHeight(36);
        t.setFont(new Font("SansSerif", Font.PLAIN, 13));
        t.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        t.getTableHeader().setBackground(new Color(241, 245, 249));
        t.getTableHeader().setForeground(TelaPrincipal.COR_TEXTO_CLARO);
        t.setGridColor(new Color(226, 232, 240));
        t.setSelectionBackground(new Color(224, 231, 255));
        t.setBackground(Color.WHITE);
        t.setShowVerticalLines(false);
    }
    

    private void estilizarBtnSecundario(JButton btn) {
        btn.setBackground(new Color(241, 245, 249));
        btn.setForeground(TelaPrincipal.COR_TEXTO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 16, 10, 16));
    }

    private JTextField campo(String valor) {
        JTextField f = new JTextField(valor);
        f.setFont(new Font("SansSerif", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225)),
            new EmptyBorder(6, 8, 6, 8)));
        return f;
    }

    private JLabel rotulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        l.setForeground(TelaPrincipal.COR_TEXTO);
        return l;
    }

    private void avisar(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Atenção", JOptionPane.WARNING_MESSAGE);
    }
}