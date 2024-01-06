package com.jmunoz.persistence.catfiledb;

public class CatException extends Exception {

    public CatException(String message) {
        super(message);
    }

    public CatException(String message, Throwable cause) {
        super(message, cause);
    }
}
