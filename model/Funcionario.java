package model;

public class Funcionario extends Pessoas{
    private String matricula;
    private String cargo;
    private double salario;

    public Funcionario(String nome, String cpf, String matricula, String cargo, Double salario){
        super(nome, cpf);
        this.matricula = matricula;
        this.cargo = cargo;
        this.salario = salario;
    }

    @Override
    public void exibirDados(){
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Matricula: " + getMatricula());
        System.out.println("Cargo: " + getCargo());
        System.out.println("Salario: " + getSalario());
    }

    public String getMatricula(){
        return matricula;
    }

    public void setMatricula(String matricula){
        this.matricula = matricula;
    }

    public String getCargo(){
        return cargo;
    }

    public void setCargo(String cargo){
        this.cargo = cargo;
    }

    public double getSalario(){
        return salario;
    }

    public void setSalario(double salario){
        this.salario = salario;
    }
}
