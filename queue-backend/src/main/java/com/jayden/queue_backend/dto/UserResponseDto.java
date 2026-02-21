package com.jayden.queue_backend.dto;

import com.jayden.queue_backend.model.Role;

public record UserResponseDto (
    Long id,
    String name,
    String email,
    String universityId,
    Role role,
    String department
) {}
