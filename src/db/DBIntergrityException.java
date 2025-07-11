package db;

public class DBIntergrityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DBIntergrityException(String mensagem) {
        super(mensagem);
    }
}
