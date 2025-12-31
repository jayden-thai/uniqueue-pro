package com.jayden.queue_backend.controller;

import com.jayden.queue_backend.model.User;
import com.jayden.queue_backend.service.UserService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {
    "http://localhost:4200",
    "http://uniqueue-frontend-jt.s3-website-us-west-1.amazonaws.com"
})
public class UserController {
    
    private final UserService studentService;

    public UserController(UserService studentService) {
        this.studentService = studentService;
    }

    // Use port 8080 (http://localhost:8080/api/students) for local testing

    @GetMapping
    public List<User> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public User joinQueue(@RequestBody User student) {
        // @RequestBody will take in JSON from Angular and create the Java object
        return studentService.addStudent(student);
    }

    @DeleteMapping("/{id}")
    public void leaveQueue (@PathVariable Long id) {
        studentService.removeStudent(id);
    }

}
