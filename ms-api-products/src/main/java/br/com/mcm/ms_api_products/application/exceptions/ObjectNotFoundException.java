package br.com.mcm.ms_api_products.application.exceptions;

public class ObjectNotFoundException extends WithoutStacktraceException {

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, String code) {
        super(String.format(message, code));
    }
}
