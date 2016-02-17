package com.java.rigor.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sanandasena on 1/11/2016.
 */
public class AbstractException extends Exception {
    private static final long serialVersionUID = -3376142403278283878L;

    private final String errorCode;
    private final List<Object> additionalData;

    public AbstractException(String errorCode, String message, Throwable t) {
        super(message, t);
        this.errorCode = errorCode;
        additionalData = new ArrayList<>();
    }

    public AbstractException(final String errorCode, final String message, final Object[] objects,
                             final Throwable t) {
        super(message, t);
        this.errorCode = errorCode;

        additionalData = new ArrayList<>();

        if (objects != null && (objects.length != 0)) {
            Collections.addAll(additionalData, objects);
        }
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object[] getObjects() {
        return additionalData.toArray();
    }
}
