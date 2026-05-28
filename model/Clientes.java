package model;

import java.io.Serializable;

/**
 * Cliente herda de Pessoas.
 * Sobrescreve exibirDados() → demonstra polimorfismo.
 */
public class Clientes extends Pessoas implements Serializable {

    private static final long serialVersionUID = 1L;

    private String endereco;
    private String telefone;
    private String email;
    private String dataNascimento;

    public Clientes(String nome, String cpf, String endereco,
                    String telefone, String email, String dataNascimento) {
        super(nome, cpf);
        this.endereco       = endereco;
        this.telefone       = telefone;
        this.email          = email;
        this.dataNascimento = dataNascimento;
    }

    @Override
    public void exibirDados() {
        System.out.println("=== CLIENTE ===");
        System.out.println("Nome: "             + getNome());
        System.out.println("CPF: "              + getCpf());
        System.out.println("Endereço: "         + endereco);
        System.out.println("Telefone: "         + telefone);
        System.out.println("Email: "            + email);
        System.out.println("Data Nascimento: "  + dataNascimento);
        System.out.println("===============");
    }

    // ─── Getters e Setters ────────────────────────────────────────────────────

    public String getEndereco()    { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone()    { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail()       { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDataNascimento()  { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    // ─── Persistência CSV ─────────────────────────────────────────────────────

    public String toCSV() {
        return getNome() + ";" + getCpf() + ";" + endereco + ";" +
               telefone + ";" + email + ";" + dataNascimento;
    }

    public static Clientes fromCSV(String linha) {
        String[] p = linha.split(";");
        return new Clientes(p[0], p[1], p[2], p[3], p[4], p[5]);
    }

    @Override
    public String toString() {
        return getNome() + " | " + email;
    }
}
