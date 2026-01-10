package com.jayden.queue_backend.service;

import com.jayden.queue_backend.dto.UserResponseDto;
import com.jayden.queue_backend.exception.DuplicateEmailException;
import com.jayden.queue_backend.exception.DuplicateUniversityIdException;
import com.jayden.queue_backend.model.User;
import com.jayden.queue_backend.repository.UserRepository;
import com.jayden.queue_backend.mapper.UserMapper;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // Seeing the database
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
            .stream()
            .map(userMapper::toDto)
            .toList();
    }

    // Removing from database
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    // Registering a new account
    public UserResponseDto registerUser(User user) {
        try {
            return userMapper.toDto(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            String msg = e.getMostSpecificCause().getMessage();

            if (msg != null && msg.contains("app_users_email_key")) {
                throw new DuplicateEmailException("Email already exists!");
            }
            if (msg != null && msg.contains("app_users_university_id_key")) {
                throw new DuplicateUniversityIdException("University ID already exists!");
            }

            throw new RuntimeException("Registration failed due to invalid data.");
        }
    }

    // Logging in by checking credentials
    public UserResponseDto login(String email, String password) {
        // Find user
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Check password
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Wrong password");
        }

        return userMapper.toDto(user);
    }

}
