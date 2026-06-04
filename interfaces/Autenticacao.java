package interfaces;

import exceptions.AutenticacaoException;
import model.Funcionario;

import java.util.List;

public class Autenticacao {
    private final List<Funcionario> funcionarios;
    private Funcionario funcionarioLogado;

    public Autenticacao(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

/* Login */
    public Funcionario login(String matricula, String senha) throws AutenticacaoException {
        if (matricula == null || senha == null|| matricula.isBlank() || senha.isBlank()){
            throw new AutenticacaoException(matricula);
        }
        
        for (Funcionario f : funcionarios){
            if (f.getMatricula().equals(matricula) && f.getSenha().equals(senha)){
                this.funcionarioLogado = f;
                System.out.println("[Sistema] Login realizado: " + f.getNome() + " (" + f.getNivelAcesso() + ")");
                return f;
            }
        }
    
        throw new AutenticacaoException(matricula);
    }

/* Logout */
    public void logout() {
        if (funcionarioLogado != null){ 
            System.out.println("[Sistema] Sessão encerrada: " + funcionarioLogado.getNome());
        }

        this.funcionarioLogado = null;
    }

/* Contsultas */
    public Funcionario getFuncionarioLogado() {
        return funcionarioLogado;
    }

    public boolean estaLogado() {
        return funcionarioLogado != null;
    }

    public boolean isAdmin(){
        return estaLogado() && "ADMIN".equals(funcionarioLogado.getNivelAcesso());
    }
}
