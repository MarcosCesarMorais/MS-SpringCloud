package br.com.mcm.ms_api_products.exceptions;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(final String message) {
        this(message, null);
    }

    public ObjectNotFoundException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }
}
