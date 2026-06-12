package main;

import model.Funcionario;
import service.Sistema;
import ui.TelaLogin;

import javax.swing.*;

/**
 * Main.java — ponto de entrada do ERP.
 * Execute esta classe para iniciar o sistema.
 */
public class Main {

    public static void main(String[] args) {

        // Inicia o sistema e carrega os dados
        Sistema sistema = new Sistema();
        sistema.carregarDados();

        // Cria o adm, para entrar no sistema
        if (sistema.getFuncionarios().isEmpty()) {
            sistema.cadastrarFuncionario(new Funcionario(
                "Administrador", "000.000.000-00",
                "F001", "Gerente", 5000.0, "admin123", "ADMIN"));

            System.out.println("     Credenciais de acesso:       ");
            System.out.println("  Admin  → F001 / admin123        ");
        }

        // Abre a tela de login
        SwingUtilities.invokeLater(() -> new TelaLogin(sistema).setVisible(true));
    }
}