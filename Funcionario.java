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
}
