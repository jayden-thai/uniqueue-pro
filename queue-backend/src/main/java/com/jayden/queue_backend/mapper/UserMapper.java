package com.jayden.queue_backend.mapper;

import org.springframework.stereotype.Component;

import com.jayden.queue_backend.dto.UserResponseDto;
import com.jayden.queue_backend.model.User;

@Component
public class UserMapper {
    
     public UserResponseDto toDto(User user) {
        return new UserResponseDto(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getUniversityId(),
            user.getRole(),
            user.getDepartment()
        );
    }

}
