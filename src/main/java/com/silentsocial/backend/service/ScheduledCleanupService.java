package com.silentsocial.backend.service;

import com.silentsocial.backend.repository.PostRepository;
import com.silentsocial.backend.repository.ResonanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ScheduledCleanupService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ResonanceRepository resonanceRepository;

    // Run every hour to clean up expired content
    @Scheduled(fixedRate = 3600000) // 3600000 ms = 1 hour
    @Transactional
    public void cleanupExpiredContent() {
        LocalDateTime now = LocalDateTime.now();
        
        // First delete resonances for expired posts
        int resonanceDeletions = resonanceRepository.deleteByPostExpiresAtBefore(now);
        
        // Then delete expired posts
        int postDeletions = postRepository.deleteByExpiresAtBefore(now);
        
        // Log the cleanup (you can add proper logging later)
        System.out.println("Cleanup completed: " + postDeletions + " posts and " + resonanceDeletions + " resonances removed.");
    }
}
