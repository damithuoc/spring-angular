package com.java.rigor.exception;

/**
 * Created by sanandasena on 1/11/2016.
 */
public class JavaRigorException extends AbstractException {

    private static final long serialVersionUID = -6970713344978936597L;

    public JavaRigorException(String errorCode, String message, Throwable t) {
        super(errorCode, message, t);
    }

    public JavaRigorException(String errorCode, String message) {
        super(errorCode, message, null);
    }

    public JavaRigorException(final String errorCode, final String message, final Object[] objects,
                              final Throwable t) {
        super(errorCode, message, objects, t);
    }
}
