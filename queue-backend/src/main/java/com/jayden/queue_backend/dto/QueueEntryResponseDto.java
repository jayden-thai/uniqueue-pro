package com.jayden.queue_backend.dto;

import java.time.Instant;

public record QueueEntryResponseDto (
    Long id,
    Long queueId,
    UserResponseDto user,
    Instant joinedAt,
    boolean active
) {}
