package com.jayden.queue_backend.repository;

import com.jayden.queue_backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Spring generates save(), findall(), delete(), and findById() automatically
}
