package br.ufrn.imd.springcrud.exception;

public class ClientException extends RuntimeException {
    public ClientException(String message) {
        super(message);
    }
}
