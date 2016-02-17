package com.java.rigor.exception;

import java.io.Serializable;

/**
 * Created by work on 2/6/16.
 */
public final class ErrorView implements Serializable {
    private static final long serialVersionUID = 4945946207553154178L;

    private String message;
    private String errorCode;

    public ErrorView(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "ErrorView{" +
                "message='" + message + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
