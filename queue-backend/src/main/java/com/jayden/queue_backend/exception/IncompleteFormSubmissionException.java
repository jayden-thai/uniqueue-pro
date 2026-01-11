package com.jayden.queue_backend.exception;

public class IncompleteFormSubmissionException extends RuntimeException{
    public IncompleteFormSubmissionException(String message) {
        super(message);
    }
}
