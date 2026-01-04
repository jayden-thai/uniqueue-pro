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

    private String role;

    @Column(nullable = false, unique = true)
    private String email;

    private String department;

    @Column(nullable = false, updatable = false)
    private Instant dateCreated;

    @PrePersist
    void onCreate() {
        if (dateCreated == null) {
            dateCreated = Instant.now();
        }
    }
}
