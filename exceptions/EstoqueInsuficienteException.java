package exceptions;

public class EstoqueInsuficienteException extends Exception {
    private final String nomeProduto;
    private final int estoqueAtual;
    private final int quantidadeSolicitada;

    public EstoqueInsuficienteException(String nomeProduto, int estoqueAtual, int quantidadeSolicitada) {
        super(String.format("O estoque está indisponível para '%s': solicitado %d, dusponível %d", nomeProduto, quantidadeSolicitada, estoqueAtual));
        this.nomeProduto = nomeProduto;
        this.estoqueAtual = estoqueAtual;
        this.quantidadeSolicitada = quantidadeSolicitada;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public int getEstoqueAtual() {
        return estoqueAtual;
    }

    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }
}
