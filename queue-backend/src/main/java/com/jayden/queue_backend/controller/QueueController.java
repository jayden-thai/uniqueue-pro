package com.jayden.queue_backend.controller;

import com.jayden.queue_backend.dto.QueueEntryResponseDto;
import com.jayden.queue_backend.service.QueueService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.ResponseErrorHandler;



@RestController
@RequestMapping("/api/queue")
@CrossOrigin(origins = {
    "http://localhost:4200",
    "http://uniqueue-frontend-jt.s3-website-us-west-1.amazonaws.com"
})
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping
    public List<QueueEntryResponseDto> getQueue() {
        return queueService.getActiveQueue();
    }

    @PostMapping("/join/{userId}")
    public ResponseEntity<QueueEntryResponseDto> join(@PathVariable Long userId) {
        QueueEntryResponseDto dto = queueService.joinQueue(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    
    @PostMapping("/leave/{userId}")
    public ResponseEntity<Void> leave(@PathVariable Long userId) {
        queueService.leaveQueue(userId);
        return ResponseEntity.noContent().build();
    }
    
}
