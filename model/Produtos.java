package model;

import java.io.Serializable;

/**
 * Produto do sistema.
 * Associado a Vendas (demonstra associação entre classes).
 */
public class Produtos implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nome;
    private double preco;
    private int estoque;
    private String categoria;

    public Produtos(String codigo, String nome, double preco, int estoque, String categoria) {
        this.codigo    = codigo;
        this.nome      = nome;
        this.preco     = preco;
        this.estoque   = estoque;
        this.categoria = categoria;
    }

    /** Construtor legado (compatível com código anterior sem codigo/categoria) */
    public Produtos(String nome, double preco, int estoque) {
        this("P" + System.currentTimeMillis(), nome, preco, estoque, "Geral");
    }

    public void exibirDados() {
        System.out.println("=== PRODUTO ===");
        System.out.println("Código:    " + codigo);
        System.out.println("Nome:      " + nome);
        System.out.printf ("Preço:     R$ %.2f%n", preco);
        System.out.println("Estoque:   " + estoque + " unidades");
        System.out.println("Categoria: " + categoria);
        System.out.println("===============");
    }

    public boolean temEstoque(int quantidade) {
        return estoque >= quantidade;
    }

    public void reduzirEstoque(int qtd) {
        if (qtd > 0 && qtd <= estoque) {
            this.estoque -= qtd;
        }
    }

    // ─── Getters e Setters ────────────────────────────────────────────────────

    public String getCodigo()    { return codigo; }

    public String getNome()      { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPreco()     { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getEstoque()      { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String cat) { this.categoria = cat; }

    // ─── Persistência CSV ─────────────────────────────────────────────────────

    public String toCSV() {
        return codigo + ";" + nome + ";" + preco + ";" + estoque + ";" + categoria;
    }

    public static Produtos fromCSV(String linha) {
        String[] p = linha.split(";");
        return new Produtos(p[0], p[1], Double.parseDouble(p[2]),
                Integer.parseInt(p[3]), p[4]);
    }

    @Override
    public String toString() {
        return "[" + codigo + "] " + nome + " - R$ " + String.format("%.2f", preco);
    }
}
