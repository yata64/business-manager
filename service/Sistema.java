package service;

import exceptions.AutenticacaoException;
import exceptions.EstoqueInsuficienteException;
import exceptions.VendaFinalizadaException;
import model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Sistema.java — controlador central da aplicação.
 *
 * Gerencia todas as listas (clientes, funcionários, produtos, vendas)
 * e delega persistência para PersistenciaService.
 *
 * Demonstra: encapsulamento, associação, exceções personalizadas.
 */
public class Sistema {

    //Listas principais (ArrayList conforme rubrica)

    private List<Clientes>    clientes    = new ArrayList<>();
    private List<Funcionario> funcionarios = new ArrayList<>();
    private List<Produtos>    produtos    = new ArrayList<>();
    private List<Vendas>      vendas      = new ArrayList<>();

    private Funcionario funcionarioLogado = null;

    //Arquivos CSV 

    private static final String ARQ_CLIENTES     = "clientes.csv";
    private static final String ARQ_FUNCIONARIOS = "funcionarios.csv";
    private static final String ARQ_PRODUTOS     = "produtos.csv";
    private static final String ARQ_VENDAS       = "vendas.csv";

    //AUTENTICAÇÃO 

    /**
     * Autentica um funcionário por matrícula e senha.
     * Lança AutenticacaoException se não encontrar correspondência.
     */
    public Funcionario login(String matricula, String senha) throws AutenticacaoException {
        for (Funcionario f : funcionarios) {
            if (f.getMatricula().equals(matricula) && f.getSenha().equals(senha)) {
                this.funcionarioLogado = f;
                System.out.println("[Sistema] Login realizado: " + f.getNome()
                        + " (" + f.getNivelAcesso() + ")");
                return f;
            }
        }
        throw new AutenticacaoException(matricula);
    }

    public void logout() {
        System.out.println("[Sistema] Sessão encerrada: "
                + (funcionarioLogado != null ? funcionarioLogado.getNome() : "ninguém"));
        this.funcionarioLogado = null;
    }

    public Funcionario getFuncionarioLogado() { return funcionarioLogado; }

    //CLIENTES 

    public void cadastrarCliente(Clientes cliente) {
        clientes.add(cliente);
        PersistenciaService.salvarDados(ARQ_CLIENTES, cliente.toCSV());
        System.out.println("[Sistema] Cliente cadastrado: " + cliente.getNome());
    }

    public Clientes buscarClientePorNome(String nome) {
        for (Clientes c : clientes) {
            if (c.getNome().equalsIgnoreCase(nome)) return c;
        }
        return null;
    }

    public List<Clientes> getClientes() { return clientes; }

    //FUNCIONÁRIOS 

    public void cadastrarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
        PersistenciaService.salvarDados(ARQ_FUNCIONARIOS, funcionario.toCSV());
        System.out.println("[Sistema] Funcionário cadastrado: " + funcionario.getNome());
    }

    public Funcionario buscarFuncionarioPorMatricula(String matricula) {
        for (Funcionario f : funcionarios) {
            if (f.getMatricula().equals(matricula)) return f;
        }
        return null;
    }

    public List<Funcionario> getFuncionarios() { return funcionarios; }

    //PRODUTOS

    public void cadastrarProduto(Produtos produto) {
        produtos.add(produto);
        PersistenciaService.salvarDados(ARQ_PRODUTOS, produto.toCSV());
        System.out.println("[Sistema] Produto cadastrado: " + produto.getNome());
    }

    public Produtos buscarProdutoPorCodigo(String codigo) {
        for (Produtos p : produtos) {
            if (p.getCodigo().equals(codigo)) return p;
        }
        return null;
    }

    /**
     * Reduz o estoque de um produto com validação.
     * Lança EstoqueInsuficienteException se não houver quantidade suficiente.
     */
    public void reduzirEstoque(Produtos produto, int quantidade)
            throws EstoqueInsuficienteException {
        if (!produto.temEstoque(quantidade)) {
            throw new EstoqueInsuficienteException(
                    produto.getNome(), produto.getEstoque(), quantidade);
        }
        produto.reduzirEstoque(quantidade);
    }

    public List<Produtos> getProdutos() { return produtos; }

    //VENDAS

    /**
     * Registra uma venda já montada.
     * Lança VendaFinalizadaException se tentar registrar venda duplicada.
     */
    public void registrarVenda(Vendas venda) throws VendaFinalizadaException {
        for (Vendas v : vendas) {
            if (v.getId() == venda.getId()) {
                throw new VendaFinalizadaException(venda.getId());
            }
        }
        vendas.add(venda);
        PersistenciaService.salvarDados(ARQ_VENDAS, venda.toCSV());
        System.out.println("[Sistema] Venda #" + venda.getId() + " registrada. Total: R$ "
                + String.format("%.2f", venda.calcularTotal()));
    }

    public List<Vendas> getVendas() { return vendas; }

    //RELATÓRIOS

    public void gerarRelatorioVendas() {
        if (vendas.isEmpty()) {
            System.out.println("[Sistema] Nenhuma venda registrada.");
            return;
        }
        System.out.println("\n========== RELATÓRIO GERAL DE VENDAS ==========");
        double totalGeral = 0;
        for (Vendas v : vendas) {
            System.out.println(v.gerarRelatorio()); // interface Relatorio
            totalGeral += v.calcularTotal();
        }
        System.out.printf("TOTAL GERAL: R$ %.2f%n", totalGeral);
        System.out.println("================================================\n");
    }

    public void listarProdutos() {
        System.out.println("\n========== PRODUTOS CADASTRADOS ==========");
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            produtos.forEach(Produtos::exibirDados);
        }
        System.out.println("==========================================\n");
    }

    public void listarClientes() {
        System.out.println("\n========== CLIENTES CADASTRADOS ==========");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            clientes.forEach(Clientes::exibirDados);
        }
        System.out.println("==========================================\n");
    }

    //PERSISTÊNCIA 

    /**
     * Carrega todos os dados dos CSVs ao iniciar o sistema.
     */
    public void carregarDados() {
        // Produtos
        for (String linha : PersistenciaService.carregarDados(ARQ_PRODUTOS)) {
            try { produtos.add(Produtos.fromCSV(linha)); } catch (Exception ignored) {}
        }

        // Clientes
        for (String linha : PersistenciaService.carregarDados(ARQ_CLIENTES)) {
            try { clientes.add(Clientes.fromCSV(linha)); } catch (Exception ignored) {}
        }

        // Funcionários
        for (String linha : PersistenciaService.carregarDados(ARQ_FUNCIONARIOS)) {
            try { funcionarios.add(Funcionario.fromCSV(linha)); } catch (Exception ignored) {}
        }

        System.out.println("[Sistema] Dados carregados — "
                + produtos.size()    + " produto(s), "
                + clientes.size()    + " cliente(s), "
                + funcionarios.size() + " funcionário(s).");
    }
}