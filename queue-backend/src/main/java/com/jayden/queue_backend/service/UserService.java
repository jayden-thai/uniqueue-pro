package com.jayden.queue_backend.service;

import com.jayden.queue_backend.model.User;
import com.jayden.queue_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.Instant;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Seeing the database
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Removing from database
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Registering a new account
    public User registerUser(User user) {
        // Check if email is already taken
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        if (user.getRole() == null) {
            user.setRole("USER");
        }

        return userRepository.save(user);
    }

    // Logging in by checking credentials
    public User login(String email, String password) {
        // Find user
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Check password
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Wrong password");
        }

        return user;
    }
}
