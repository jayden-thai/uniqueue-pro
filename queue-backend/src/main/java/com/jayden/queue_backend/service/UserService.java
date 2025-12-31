package com.jayden.queue_backend.service;

import com.jayden.queue_backend.model.User;
import com.jayden.queue_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    
    private final UserRepository studentRepository;

    public UserService(UserRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Joining the queue
    public User addStudent(User student) {
        if (student.getArrivalTime() == null) 
            student.setArrivalTime(java.time.LocalDateTime.now());
        return studentRepository.save(student);
    }

    // Seeing the queue
    public List<User> getAllStudents() {
        return studentRepository.findAll();
    }

    // Removing from queue
    public void removeStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public User getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    // Registering a new account
    public User registerStudent(User student) {
        // Check if email is already taken
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        if (student.getRole() == null) {
            student.setRole("STUDENT");
        }

        return studentRepository.save(student);
    }

    // Logging in by checking credentials
    public User login(String email, String password) {
        // Find user
        User student = studentRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Check password
        if (!student.getPassword().equals(password)) {
            throw new RuntimeException("Wrong password");
        }

        return student;
    }
}
