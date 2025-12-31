package com.jayden.queue_backend.controller;

import com.jayden.queue_backend.model.Student;
import com.jayden.queue_backend.service.StudentService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = {
    "http://localhost:4200",
    "http://uniqueue-frontend-jt.s3-website-us-west-1.amazonaws.com"
})
public class StudentController {
    
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Use port 8080 (http://localhost:8080/api/students)

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public Student joinQueue(@RequestBody Student student) {
        // @RequestBody will take in JSON from Angular and create the Java object
        return studentService.addStudent(student);
    }

    @DeleteMapping("/{id}")
    public void leaveQueue (@PathVariable Long id) {
        studentService.removeStudent(id);
    }

}
