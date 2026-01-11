package com.jayden.queue_backend.service;

import com.jayden.queue_backend.dto.QueueEntryResponseDto;
import com.jayden.queue_backend.exception.DuplicateQueueEntryException;
import com.jayden.queue_backend.exception.QueueEntryNotFoundException;
import com.jayden.queue_backend.exception.UserNotFoundException;
import com.jayden.queue_backend.mapper.QueueEntryMapper;
import com.jayden.queue_backend.model.QueueEntry;
import com.jayden.queue_backend.model.User;
import com.jayden.queue_backend.repository.QueueEntryRepository;
import com.jayden.queue_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueService {
    
    private final QueueEntryRepository queueEntryRepository;
    private final UserRepository userRepository;
    private final QueueEntryMapper queueEntryMapper;

    public QueueService(QueueEntryRepository queueEntryRepository, UserRepository userRepository, QueueEntryMapper queueEntryMapper) {
        this.queueEntryRepository = queueEntryRepository;
        this.userRepository = userRepository;
        this.queueEntryMapper = queueEntryMapper;
    }

    public List<QueueEntryResponseDto> getActiveQueue() {
        return queueEntryRepository.findByActiveTrueOrderByJoinedAtAsc()
            .stream()
            .map(queueEntryMapper::toDto)
            .toList();
    }

    public QueueEntryResponseDto joinQueue(Long userId) {
        queueEntryRepository.findByUserIdAndActiveTrue(userId).ifPresent(e -> {
            throw new DuplicateQueueEntryException("User is already in the queue");
        });

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
            
        QueueEntry entry = new QueueEntry();
        entry.setUser(user);
        entry.setActive(true);

        return queueEntryMapper.toDto(queueEntryRepository.save(entry));
    }

    public void leaveQueue(Long userId) {
        QueueEntry entry = queueEntryRepository.findByUserIdAndActiveTrue(userId)
                        .orElseThrow(() -> new QueueEntryNotFoundException("User is not in the queue."));
        
        entry.setActive(false);
        queueEntryRepository.save(entry);
    }
}
