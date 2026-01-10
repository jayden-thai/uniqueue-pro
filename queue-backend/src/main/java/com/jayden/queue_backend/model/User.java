package com.jayden.queue_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;


@Entity
@Table(name = "app_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true) 
    private String universityId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.STUDENT;

    @Column(nullable = false, unique = true)
    private String email;

    private String department;

    @Column(nullable = false, updatable = false)
    private Instant dateCreated;

    @PrePersist
    void onCreate() {
        if (role == null) {
            role = Role.STUDENT;
        }
        if (dateCreated == null) {
            dateCreated = Instant.now();
        }
    }
}
