package com.jayden.queue_backend.exception;

public class QueueEntryNotFoundException extends RuntimeException {
    public QueueEntryNotFoundException (String message) {
        super(message);
    }
}
