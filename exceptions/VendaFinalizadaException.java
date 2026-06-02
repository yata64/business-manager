package exceptions;

public class VendaFinalizadaException extends Exception{
    private final int idVenda;

    public VendaFinalizadaException(int idVenda) {
        super(String.format("A venda #%d já está CONCLUÍDA e não pode ser modificada.", idVenda));
        this.idVenda = idVenda;
    }

    public int getIdVenda() {
        return idVenda;
    }
}
