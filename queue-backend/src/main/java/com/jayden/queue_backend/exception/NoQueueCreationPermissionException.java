package com.jayden.queue_backend.exception;

public class NoQueueCreationPermissionException extends RuntimeException{
    public NoQueueCreationPermissionException(String message) {
        super(message);
    }
}
