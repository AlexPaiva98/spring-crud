package br.ufrn.imd.springcrud.exception;

public class ValidationException extends BusinessException {
    public ValidationException(String message) {
        super(message);
    }
}
