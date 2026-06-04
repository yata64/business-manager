package model;

import java.io.Serializable;

/**
 * Funcionario herda de Pessoas e implementa Autenticavel.
 * Demonstra: herança + interface + encapsulamento + polimorfismo.
 */
public class Funcionario extends Pessoas implements Serializable {

    private static final long serialVersionUID = 1L;

    private String matricula;
    private String cargo;
    private double salario;
    private String senha;         // necessário para autenticação
    private String nivelAcesso;   // "ADMIN" ou "VENDEDOR"

    public Funcionario(String nome, String cpf, String matricula,
                       String cargo, double salario, String senha, String nivelAcesso) {
        super(nome, cpf);
        this.matricula   = matricula;
        this.cargo       = cargo;
        this.salario     = salario;
        this.senha       = senha;
        this.nivelAcesso = nivelAcesso;
    }

    /** Construtor legado (compatível com código anterior sem senha) */
    public Funcionario(String nome, String cpf, String matricula, String cargo, double salario) {
        this(nome, cpf, matricula, cargo, salario, "1234", "VENDEDOR");
    }

    // ─── Sobrescrita de exibirDados() → polimorfismo ──────────────────────────

    @Override
    public void exibirDados() {
        System.out.println("=== FUNCIONÁRIO ===");
        System.out.println("Nome:      " + getNome());
        System.out.println("CPF:       " + getCpf());
        System.out.println("Matrícula: " + matricula);
        System.out.println("Cargo:     " + cargo);
        System.out.printf ("Salário:   R$ %.2f%n", salario);
        System.out.println("Acesso:    " + nivelAcesso);
        System.out.println("==================");
    }

    // ─── Getters e Setters ────────────────────────────────────────────────────

    public String getMatricula()   { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getCargo()       { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public double getSalario()     { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getNivelAcesso() { return nivelAcesso; }
    public void setNivelAcesso(String nivelAcesso) { this.nivelAcesso = nivelAcesso; }

    // ─── Persistência CSV ─────────────────────────────────────────────────────

    public String toCSV() {
        return getNome() + ";" + getCpf() + ";" + matricula + ";" +
               cargo + ";" + salario + ";" + senha + ";" + nivelAcesso;
    }

    public static Funcionario fromCSV(String linha) {
        String[] p = linha.split(";");
        return new Funcionario(p[0], p[1], p[2], p[3],
                Double.parseDouble(p[4]), p[5], p[6]);
    }

    @Override
    public String toString() {
        return "[" + matricula + "] " + getNome() + " - " + cargo;
    }
}
