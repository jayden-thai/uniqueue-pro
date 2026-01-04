package com.jayden.queue_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "queue_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueueEntry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable = false)
    private Instant joinedAt;

    @Column(nullable = false)
    private boolean active = true;

    @PrePersist
    void onCreate() {
        if (joinedAt == null) joinedAt = Instant.now();
    }
}
