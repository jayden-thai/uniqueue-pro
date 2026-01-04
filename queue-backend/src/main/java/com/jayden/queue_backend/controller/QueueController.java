package com.jayden.queue_backend.controller;

import com.jayden.queue_backend.model.QueueEntry;
import com.jayden.queue_backend.service.QueueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
    public List<QueueEntry> getQueue() {
        return queueService.getActiveQueue();
    }

    @PostMapping("/join/{userId}")
    public QueueEntry join(@PathVariable Long userId) {
        return queueService.joinQueue(userId);
    }
    
    @PostMapping("/leave/{userId}")
    public void leave(@PathVariable Long userId) {
        queueService.leaveQueue(userId);
    }
    
}
