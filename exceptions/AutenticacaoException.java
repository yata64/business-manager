package exceptions;

public class AutenticacaoException extends Exception {
    private final String usuario;

    public AutenticacaoException(String usuario) {
        super(String.format("Usuário '%s' não encontrado. Erro na matricula ou senha incorreta !!!", usuario));
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }
}
