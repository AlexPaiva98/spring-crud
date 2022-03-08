package br.ufrn.imd.springcrud.util;

public final class MessageExceptionUtil {
    public static String getMessage(Throwable exception) {
        String message = exception.getMessage();
        if (message.isEmpty()) {
            return "Message not informed";
        }
        return message;
    }
}
