package com.jayden.queue_backend.dto;

import java.time.Instant;

public record QueueResponseDto (
    Long id,
    UserResponseDto owner,
    String title,
    Instant createdAt,
    boolean active
) {}
