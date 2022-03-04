package br.ufrn.imd.springcrud.exception;

public class EntityNotFoundException extends GenericException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entity, String id) {
        super("Entity '" + entity + "' of id=" + id + " not found");
    }
}
