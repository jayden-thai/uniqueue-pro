package com.jayden.queue_backend.exception;

public class DuplicateQueueEntryException extends RuntimeException{
    public DuplicateQueueEntryException (String message) {
        super(message);
    }
}
