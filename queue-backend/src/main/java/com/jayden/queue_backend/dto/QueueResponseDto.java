package com.jayden.queue_backend.dto;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueueResponseDto {
    private Long id;
    private UserResponseDto owner;
    private String title;
    private Instant createdAt;
    private List<QueueEntryResponseDto> entries;
}
