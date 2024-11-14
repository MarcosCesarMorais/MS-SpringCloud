package br.com.mcm.ms_api_products.application.exceptions;

public class WithoutStacktraceException extends RuntimeException {
    public WithoutStacktraceException(final String message) {
        this(message, null);
    }

    public WithoutStacktraceException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }
}
