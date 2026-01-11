package com.jayden.queue_backend.dto;

import lombok.Data;

import java.time.Instant;

import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class QueueEntryResponseDto {
    private Long id;
    private UserResponseDto user;
    private Instant joinedAt;
    private boolean active;
}
