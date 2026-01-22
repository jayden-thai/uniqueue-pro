package com.jayden.queue_backend.controller;

import com.jayden.queue_backend.dto.QueueEntryResponseDto;
import com.jayden.queue_backend.dto.QueueResponseDto;
import com.jayden.queue_backend.service.QueueService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/api/queues")
@CrossOrigin(origins = {
    "http://localhost:4200",
    "http://uniqueue-frontend-jt.s3-website-us-west-1.amazonaws.com"
})
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping("")
    public ResponseEntity<QueueResponseDto> createQueue(@RequestParam Long ownerId, @RequestParam String title) {
        QueueResponseDto q = queueService.createQueue(ownerId, title);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(q);
    }
    
    @GetMapping("/owner/{ownerId}")
    public List<QueueResponseDto> getQueuesForOwner(@PathVariable Long ownerId) {
        return queueService.getQueuesForOwner(ownerId);
    }

    @GetMapping("/{queueId}/entries")
    public List<QueueEntryResponseDto> getEntries(@PathVariable Long queueId) {
        return queueService.getQueueEntries(queueId);
    }
    

    @PostMapping("/{queueId}/join/{userId}")
    public ResponseEntity<QueueEntryResponseDto> join(@PathVariable Long queueId, @PathVariable Long userId) {
        QueueEntryResponseDto dto = queueService.joinQueue(queueId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    
    @PostMapping("/{queueId}/leave/{userId}")
    public ResponseEntity<Void> leave(@PathVariable Long queueId, @PathVariable Long userId) {
        queueService.leaveQueue(queueId, userId);
        return ResponseEntity.noContent().build();
    }
    
}
