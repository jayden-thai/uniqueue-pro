package com.jayden.queue_backend.exception;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException (String message) {
        super(message);
    }
}
