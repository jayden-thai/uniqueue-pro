package com.jayden.queue_backend.controller;

import com.jayden.queue_backend.model.User;
import com.jayden.queue_backend.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void leaveQueue (@PathVariable Long id) {
        userService.removeUser(id);
    }

    @PostMapping("/login")
    public User login(@RequestBody User loginRequest) {
        return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }
    

}
