package com.jayden.queue_backend.service;

import com.jayden.queue_backend.dto.UserResponseDto;
import com.jayden.queue_backend.exception.DuplicateEmailException;
import com.jayden.queue_backend.exception.DuplicateUniversityIdException;
import com.jayden.queue_backend.exception.IncompleteFormSubmissionException;
import com.jayden.queue_backend.exception.InvalidCredentialException;
import com.jayden.queue_backend.exception.UserNotFoundException;
import com.jayden.queue_backend.model.Role;
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
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.deleteById(id);
    }

    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    public List<UserResponseDto> getFaculty(String search) {
        
        String s = (search == null) ? null : search.trim();

        return userRepository
            .searchFaculty(Role.FACULTY, s)
            .stream()
            .map(userMapper::toDto)
            .toList();
    }

    // Registering a new account
    public UserResponseDto registerUser(User user) {

        if (user.getEmail() == null || user.getEmail().isBlank()
        || user.getPassword() == null || user.getPassword().isBlank()
        || user.getUniversityId() == null || user.getUniversityId().isBlank()
        || user.getName() == null || user.getName().isBlank()) {
            throw new IncompleteFormSubmissionException("Missing required fields.");
        }

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
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new IncompleteFormSubmissionException("Email and password are required.");
        }
        
        // Find user
        User user = userRepository.findByEmail(email).orElseThrow(() -> new InvalidCredentialException("Invalid credentials"));

        // Check password
        if (!user.getPassword().equals(password)) {
            throw new InvalidCredentialException("Invalid credentials");
        }

        return userMapper.toDto(user);
    }

}
