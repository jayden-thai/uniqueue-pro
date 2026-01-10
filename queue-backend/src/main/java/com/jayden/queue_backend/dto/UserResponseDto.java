package com.jayden.queue_backend.dto;

import com.jayden.queue_backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String universityId;
    private Role role;
    private String department;
}
