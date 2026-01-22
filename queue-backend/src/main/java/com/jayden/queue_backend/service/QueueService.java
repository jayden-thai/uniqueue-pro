package com.jayden.queue_backend.service;

import com.jayden.queue_backend.dto.QueueEntryResponseDto;
import com.jayden.queue_backend.dto.QueueResponseDto;
import com.jayden.queue_backend.exception.DuplicateQueueEntryException;
import com.jayden.queue_backend.exception.NoQueueCreationPermissionException;
import com.jayden.queue_backend.exception.QueueEntryNotFoundException;
import com.jayden.queue_backend.exception.QueueNotFoundException;
import com.jayden.queue_backend.exception.UserNotFoundException;
import com.jayden.queue_backend.mapper.QueueEntryMapper;
import com.jayden.queue_backend.mapper.QueueMapper;
import com.jayden.queue_backend.model.*;
import com.jayden.queue_backend.repository.QueueEntryRepository;
import com.jayden.queue_backend.repository.QueueRepository;
import com.jayden.queue_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueService {
    
    private final QueueEntryRepository queueEntryRepository;
    private final UserRepository userRepository;
    private final QueueEntryMapper queueEntryMapper;
    private final QueueRepository queueRepository;
    private final QueueMapper queueMapper;

    public QueueService(QueueEntryRepository queueEntryRepository, UserRepository userRepository, QueueEntryMapper queueEntryMapper, QueueRepository queueRepository, QueueMapper queueMapper) {
        this.queueEntryRepository = queueEntryRepository;
        this.userRepository = userRepository;
        this.queueEntryMapper = queueEntryMapper;
        this.queueRepository = queueRepository;
        this.queueMapper = queueMapper;
    }

    public QueueResponseDto createQueue(Long ownerId, String title) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new UserNotFoundException("Owner not found"));

        if (owner.getRole() != Role.FACULTY) {
            throw new NoQueueCreationPermissionException("Only faculty can create queues");
        }

        Queue q = new Queue();
        q.setOwner(owner);
        q.setTitle(title);
        q.setActive(true);

        return queueMapper.toDto(q);
    }

    public QueueResponseDto getActiveQueueForOwner(Long ownerId) {
        Queue q = queueRepository.findByOwnerIdAndActiveTrue(ownerId)
            .orElseThrow(() -> new QueueNotFoundException("Queue not found"));
        return queueMapper.toDto(q);
    }

    public List<QueueResponseDto> getQueuesForOwner(Long ownerId) {
        return queueRepository.findByOwnerId(ownerId)
            .stream()
            .map(queueMapper::toDto)
            .toList();
    }

    public List<QueueEntryResponseDto> getQueueEntries(Long queueId) {
        return queueEntryRepository.findByQueueIdAndActiveTrueOrderByJoinedAtAsc(queueId)
            .stream()
            .map(queueEntryMapper::toDto)
            .toList();
    }

    public QueueEntryResponseDto joinQueue(Long queueId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Queue queue = queueRepository.findById(userId)
                .orElseThrow(() -> new QueueNotFoundException("Queue not found"));
        
        queueEntryRepository.findByQueueIdAndUserIdAndActiveTrue(queueId, userId).ifPresent(e -> {
            throw new DuplicateQueueEntryException("User is already in the queue");
        });

        
            
        QueueEntry entry = new QueueEntry();
        entry.setQueue(queue);
        entry.setUser(user);
        entry.setActive(true);

        return queueEntryMapper.toDto(queueEntryRepository.save(entry));
    }

    public void leaveQueue(Long queueId, Long userId) {
        QueueEntry entry = queueEntryRepository.findByQueueIdAndUserIdAndActiveTrue(queueId, userId)
                        .orElseThrow(() -> new QueueEntryNotFoundException("User is not in the queue."));
        
        entry.setActive(false);
        queueEntryRepository.save(entry);
    }
}
