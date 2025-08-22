package com.silentsocial.backend.repository;

import com.silentsocial.backend.model.Resonance;
import com.silentsocial.backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface ResonanceRepository extends JpaRepository<Resonance, UUID> {

    // Count resonances for a specific post
    long countByPostId(UUID postId);

    // Check if a post has any resonances (for sorting/popularity)
    boolean existsByPostId(UUID postId);

    // Delete resonances for a specific post
    void deleteByPost(Post post);

    // Delete resonances for expired posts (for scheduled cleanup) - returns count
    @Modifying
    @Query("DELETE FROM Resonance r WHERE r.post.expiresAt < :currentTime")
    int deleteByPostExpiresAtBefore(@Param("currentTime") LocalDateTime currentTime);
}
