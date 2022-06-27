package com.mchelushkin.urlshortener.model.exception;

public class InvalidUrlException extends RuntimeException {

    public InvalidUrlException(String message) {
        super(message);
    }

}
