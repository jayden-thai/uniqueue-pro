package com.jayden.queue_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class) 
    public ResponseEntity<String> handleDuplicateEmail(DuplicateEmailException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(DuplicateUniversityIdException.class) 
    public ResponseEntity<String> handleDuplicateUniveresityId(DuplicateUniversityIdException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    
    @ExceptionHandler(DuplicateQueueEntryException.class)
    public ResponseEntity<String> handleDuplicateQueueEntry(DuplicateQueueEntryException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); 
    }

    @ExceptionHandler(QueueEntryNotFoundException.class) 
    public ResponseEntity<String> handleQueueEntryNotFound(QueueEntryNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class) 
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InvalidCredentialException.class) 
    public ResponseEntity<String> handleInvalidCredential(InvalidCredentialException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(IncompleteFormSubmissionException.class) 
    public ResponseEntity<String> handleIncompleteFormSubmission(IncompleteFormSubmissionException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }

}
