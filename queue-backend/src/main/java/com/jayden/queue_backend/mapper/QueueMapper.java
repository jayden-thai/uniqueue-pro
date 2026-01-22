package com.jayden.queue_backend.mapper;

import org.springframework.stereotype.Component;

import com.jayden.queue_backend.dto.QueueResponseDto;
import com.jayden.queue_backend.model.Queue;
import com.jayden.queue_backend.repository.QueueEntryRepository;

@Component
public class QueueMapper {

    private final QueueEntryRepository queueEntryRepository;
    private final QueueEntryMapper queueEntryMapper;
    private final UserMapper userMapper;

    public QueueMapper(QueueEntryRepository queueEntryRepository, QueueEntryMapper queueEntryMapper, UserMapper userMapper) {
        this.queueEntryRepository = queueEntryRepository;
        this.queueEntryMapper = queueEntryMapper;
        this.userMapper = userMapper;
    }

    public QueueResponseDto toDto(Queue queue) {

        return new QueueResponseDto(
            queue.getId(),
            userMapper.toDto(queue.getOwner()),
            queue.getTitle(),
            queue.getCreatedAt(),
            queueEntryRepository.findByQueueIdAndActiveTrueOrderByJoinedAtAsc(queue.getId())
            .stream()
            .map(queueEntryMapper::toDto)
            .toList()
        );
    }
}
