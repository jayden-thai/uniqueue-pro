package com.jayden.queue_backend.controller;

import com.jayden.queue_backend.model.User;
import com.jayden.queue_backend.service.UserService;
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

    public UserController(UserService studentService) {
        this.userService = studentService;
    }

    // Use port 8080 (http://localhost:8080/api/students) for localhost testing

    @GetMapping
    public List<User> getAllStudents() {
        return userService.getAllStudents();
    }

    @PostMapping
    public User joinQueue(@RequestBody User student) {
        // @RequestBody will take in JSON from Angular and create the Java object
        return userService.addStudent(student);
    }

    @DeleteMapping("/{id}")
    public void leaveQueue (@PathVariable Long id) {
        userService.removeStudent(id);
    }

    @PostMapping("/login")
    public User login(@RequestBody User loginRequest) {
        return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }
    

}
