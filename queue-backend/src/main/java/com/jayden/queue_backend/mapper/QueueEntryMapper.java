package com.jayden.queue_backend.mapper;

import org.springframework.stereotype.Component;

import com.jayden.queue_backend.dto.QueueEntryResponseDto;
import com.jayden.queue_backend.model.QueueEntry;

@Component
public class QueueEntryMapper {

    private final UserMapper userMapper;

    public QueueEntryMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    public QueueEntryResponseDto toDto(QueueEntry queueEntry) {
        return new QueueEntryResponseDto(
            queueEntry.getId(),
            queueEntry.getQueue().getId(),
            userMapper.toDto(queueEntry.getUser()),
            queueEntry.getJoinedAt(),
            queueEntry.isActive()
        );
    }
}
