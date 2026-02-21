package com.jayden.queue_backend.controller;

import com.jayden.queue_backend.dto.UserResponseDto;
import com.jayden.queue_backend.model.User;
import com.jayden.queue_backend.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {
    "http://localhost:4200",
    "http://uniqueue-frontend-jt.s3-website-us-west-1.amazonaws.com"
})
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Use port 8080 (http://localhost:8080/api/users) for localhost testing

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/faculty")
    public List<UserResponseDto> getFaculty(@RequestParam(required = false) String search) {
        return userService.getFaculty(search);
    }
    

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        UserResponseDto newUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable Long id) {
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        return ResponseEntity.ok(
            userService.login(loginRequest.getEmail(), loginRequest.getPassword())
        );
    }
    

}
