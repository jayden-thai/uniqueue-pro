package com.jayden.queue_backend.repository;

import com.jayden.queue_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring generates save(), findall(), delete(), and findById() automatically

    // "Optional" acts like a  "Safety Box" in case the user does not exist (prevents null pointer crashes)
    Optional<User> findByEmail(String Email);
    Optional<User> findByUniversityId(String universityId);
}
