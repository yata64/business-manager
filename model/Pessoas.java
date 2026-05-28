package model;

import java.io.Serializable;

/**
 * Classe ABSTRATA base do sistema.
 * Todas as entidades do sistema são Pessoas.
 *
 * Demonstra: abstração, encapsulamento, herança.
 */
public abstract class Pessoas implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String cpf;

    public Pessoas(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    /**
     * Método ABSTRATO → cada subclasse OBRIGATORIAMENTE sobrescreve.
     * Demonstra: polimorfismo + sobrescrita (@Override).
     */
    public abstract void exibirDados();

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    @Override
    public String toString() {
        return nome + " (CPF: " + cpf + ")";
    }
}
