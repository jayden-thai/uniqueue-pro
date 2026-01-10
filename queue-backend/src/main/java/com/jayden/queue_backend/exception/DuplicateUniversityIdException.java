package com.jayden.queue_backend.exception;

public class DuplicateUniversityIdException extends RuntimeException {
    public DuplicateUniversityIdException (String message) {
        super(message);
    }
}
