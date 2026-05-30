package model;

import interfaces.Relatorio;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Venda possui ASSOCIAÇÃO com:
 *   - Clientes  → quem comprou
 *   - Funcionario → quem vendeu
 *   - ArrayList<Produtos> → o que foi vendido
 *
 * Implementa Relatoravel → demonstra uso de interface.
 * BUG CORRIGIDO: produto.preco() → produto.getPreco()
 */
public class Vendas implements Relatorio, Serializable {

    private static final long serialVersionUID = 1L;

    public static int contadorVendas = 0;

    private int id;
    private Clientes cliente;
    private Funcionario funcionario;
    private ArrayList<Produtos> produtos; // ArrayList conforme rubrica
    private LocalDate dataVenda;
    private String status;

    public Vendas(Clientes cliente, Funcionario funcionario) {
        this.id          = ++contadorVendas;
        this.cliente     = cliente;
        this.funcionario = funcionario;
        this.produtos    = new ArrayList<>();
        this.dataVenda   = LocalDate.now();
        this.status      = "CONCLUIDA";
    }

    public void adicionarProduto(Produtos produto) {
        produtos.add(produto);
    }

    public void removerProduto(Produtos produto) {
        produtos.remove(produto);
    }

    /**
     * BUG CORRIGIDO: era produto.preco() — método não existe.
     * Correto: produto.getPreco()
     */
    public double calcularTotal() {
        double total = 0;
        for (Produtos produto : produtos) {
            total += produto.getPreco(); // ← CORREÇÃO DO BUG
        }
        return total;
    }

    // ─── Implementação da interface Relatoravel ───────────────────────────────

    @Override
    public String gerarRelatorio() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("           RELATÓRIO DE VENDA           \n");
        sb.append("========================================\n");
        sb.append("ID Venda   : #").append(id).append("\n");
        sb.append("Data       : ").append(dataVenda.format(fmt)).append("\n");
        sb.append("Cliente    : ").append(cliente.getNome()).append("\n");
        sb.append("Vendedor   : ").append(funcionario.getNome()).append("\n");
        sb.append("Status     : ").append(status).append("\n");
        sb.append("----------------------------------------\n");
        sb.append("PRODUTOS:\n");
        for (Produtos p : produtos) {
            sb.append(String.format("  %-25s R$ %.2f%n", p.getNome(), p.getPreco()));
        }
        sb.append("----------------------------------------\n");
        sb.append(String.format("TOTAL      : R$ %.2f%n", calcularTotal()));
        sb.append("========================================\n");
        return sb.toString();
    }

    // ─── Getters e Setters ────────────────────────────────────────────────────

    public int getId()                       { return id; }
    public void setId(int id)               { this.id = id; }

    public Clientes getCliente()             { return cliente; }
    public void setCliente(Clientes c)      { this.cliente = c; }

    public Funcionario getFuncionario()      { return funcionario; }
    public void setFuncionario(Funcionario f){ this.funcionario = f; }

    public ArrayList<Produtos> getProdutos() { return produtos; }
    public void setProdutos(ArrayList<Produtos> produtos) { this.produtos = produtos; }

    public LocalDate getDataVenda()          { return dataVenda; }
    public void setDataVenda(LocalDate d)   { this.dataVenda = d; }

    public String getStatus()               { return status; }
    public void setStatus(String status)    { this.status = status; }

    public String toCSV() {
        return id + ";" + cliente.getNome() + ";" + funcionario.getNome() +
               ";" + dataVenda + ";" + String.format("%.2f", calcularTotal()) + ";" + status;
    }

    @Override
    public String toString() {
        return "Venda #" + id + " | " + cliente.getNome() +
               " | R$ " + String.format("%.2f", calcularTotal());
    }
}
