package com.jayden.queue_backend.repository;

import com.jayden.queue_backend.model.QueueEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QueueEntryRepository extends JpaRepository<QueueEntry, Long>{

    List<QueueEntry> findByQueueIdAndActiveTrueOrderByJoinedAtAsc(Long queueId);

    Optional<QueueEntry> findByQueueIdAndUserIdAndActiveTrue(Long queueId, Long userId);
} 
