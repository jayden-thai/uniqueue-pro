package com.jayden.queue_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jayden.queue_backend.model.Queue;

public interface QueueRepository extends JpaRepository<Queue, Long>{
    List<Queue> findByOwnerId(Long ownerId);
    Optional<Queue> findByOwnerIdAndActiveTrue(Long ownerId);
}
