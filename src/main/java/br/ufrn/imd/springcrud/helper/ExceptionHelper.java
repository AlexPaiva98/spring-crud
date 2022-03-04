package br.ufrn.imd.springcrud.helper;

public class ExceptionHelper {
    private String message;

    public ExceptionHelper() {
        this.setMessage("");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void add(String message) {
        if (!this.message.isEmpty()) {
            this.message += ", ";
        }
        this.message += message;
    }

    public boolean isEmpty() {
        return this.message.isEmpty();
    }
}
