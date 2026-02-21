package com.jayden.queue_backend.repository;

import com.jayden.queue_backend.model.Role;
import com.jayden.queue_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring generates save(), findall(), delete(), and findById() automatically

    // "Optional" acts like a  "Safety Box" in case the user does not exist (prevents null pointer crashes)
    Optional<User> findByEmail(String email);
    Optional<User> findByUniversityId(String universityId);

    // Find by role/name or role/department with explicit query
    @Query("""
            select u from User u
            where u.role = :role
                and (
                    :search is null
                    or :search = ''
                    or lower(u.name) like lower (concat('%', :search, '%'))
                    or lower(coalesce(u.department, '')) like lower(concat('%', :search, '%'))
                )
            """)
    List<User> searchFaculty(@Param("role") Role role, @Param("search") String search);
}   
