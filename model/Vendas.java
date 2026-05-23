package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Vendas {
    public static int contadorVendas = 0;

    private int id;
    private Clientes Cliente;
    private Funcionario Funcionario;
    private List<Produtos> produtos;
    private LocalDate dataVenda;

    public Vendas(Clientes cliente, Funcionario funcionario) {
        this.id = contadorVendas++;
        this.Cliente = cliente;
        this.Funcionario = funcionario;
        this.produtos = new ArrayList<>();
        this.dataVenda = LocalDate.now();
    }

    public void adicionarProduto(Produtos produto) {
        produtos.add(produto);
    }

    public void removerProduto(Produtos produto) {
        produtos.remove(produto);
    }

    public double calcularTotal(){
        double total = 0;
        for (Produtos produto : produtos){
            total += produto.preco();
        }
        return total;
    }

    public int getID(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }

    public Clientes getCliente(){
        return Cliente;
    }

    public void setCliente(Clientes cliente){
        this.Cliente = cliente;
    }

    public Funcionario getFuncionario(){
        return Funcionario;
    }

    public void setFuncionario(Funcionario funcionario){
        this.Funcionario = funcionario;
    }

    public List<Produtos> getProdutos(){
        return produtos;
    }

    public void setProdutos(List<Produtos> produtos){
        this.produtos = produtos;
    }

    public LocalDate getDataVenda(){
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda){
        this.dataVenda = dataVenda;
    }
}