package com.jayden.queue_backend.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException (String message) {
        super(message);
    }
}
