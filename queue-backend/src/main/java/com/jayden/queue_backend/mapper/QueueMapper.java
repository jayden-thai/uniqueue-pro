package com.jayden.queue_backend.mapper;

import org.springframework.stereotype.Component;

import com.jayden.queue_backend.dto.QueueResponseDto;
import com.jayden.queue_backend.model.Queue;

@Component
public class QueueMapper {

    private final UserMapper userMapper;

    public QueueMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public QueueResponseDto toDto(Queue queue) {

        return new QueueResponseDto(
            queue.getId(),
            userMapper.toDto(queue.getOwner()),
            queue.getTitle(),
            queue.getCreatedAt()
        );
    }
}
