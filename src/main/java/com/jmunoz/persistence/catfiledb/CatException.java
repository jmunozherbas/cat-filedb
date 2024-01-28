package com.jmunoz.persistence.catfiledb;

public class CatException extends Exception {

    private String codeException;

    public CatException(String codeException, String message) {
        super(message);
        this.codeException = codeException;
    }

    public CatException(String codeException, String message, Throwable cause) {
        super(message, cause);
        this.codeException = codeException;
    }

    public String getCodeException() {
        return codeException;
    }

}
