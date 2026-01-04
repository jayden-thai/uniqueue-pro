package com.jayden.queue_backend.service;

import com.jayden.queue_backend.model.QueueEntry;
import com.jayden.queue_backend.model.User;
import com.jayden.queue_backend.repository.QueueEntryRepository;
import com.jayden.queue_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.management.RuntimeErrorException;

@Service
public class QueueService {
    
    private final QueueEntryRepository queueEntryRepository;
    private final UserRepository userRepository;

    public QueueService(QueueEntryRepository queueEntryRepository, UserRepository userRepository) {
        this.queueEntryRepository = queueEntryRepository;
        this.userRepository = userRepository;
    }

    public List<QueueEntry> getActiveQueue() {
        return queueEntryRepository.findByActiveTrueOrderByJoinedAtAsc();
    }

    public QueueEntry joinQueue(Long userId) {
        queueEntryRepository.findByUserIdAndActiveTrue(userId).ifPresent(e -> {
            throw new RuntimeException("User is already in the queue");
        });

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
        QueueEntry entry = new QueueEntry();
        entry.setUser(user);
        entry.setActive(true);

        return queueEntryRepository.save(entry);
    }

    public void leaveQueue(Long userId) {
        QueueEntry entry = queueEntryRepository.findByUserIdAndActiveTrue(userId)
                        .orElseThrow(() -> new RuntimeException("User is not in the queue."));
        
        entry.setActive(false);
        queueEntryRepository.save(entry);
    }
}
