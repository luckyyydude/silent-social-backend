package com.silentsocial.backend.repository;

import com.silentsocial.backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    // Find all non-expired posts, ordered by newest first
    List<Post> findByExpiresAtAfterOrderByCreatedAtDesc(LocalDateTime currentTime);

    // Find posts by content keyword (for future search functionality)
    List<Post> findByContentContainingIgnoreCase(String keyword);

    // Delete expired posts (for scheduled cleanup) - returns count of deleted rows
    @Modifying
    @Query("DELETE FROM Post p WHERE p.expiresAt < :currentTime")
    int deleteByExpiresAtBefore(@Param("currentTime") LocalDateTime currentTime);

    // Count all non-expired posts
    long countByExpiresAtAfter(LocalDateTime currentTime);
}
