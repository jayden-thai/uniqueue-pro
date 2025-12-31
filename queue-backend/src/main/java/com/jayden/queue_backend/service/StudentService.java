package com.jayden.queue_backend.service;

import com.jayden.queue_backend.model.Student;
import com.jayden.queue_backend.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {
    
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Joining the queue
    public Student addStudent(Student student) {
        if (student.getArrivalTime() == null) 
            student.setArrivalTime(java.time.LocalDateTime.now());
        return studentRepository.save(student);
    }

    // Seeing the queue
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Removing from queue
    public void removeStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }
}
