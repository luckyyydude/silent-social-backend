package com.silentsocial.backend.controller;

import com.silentsocial.backend.model.Post;
import com.silentsocial.backend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    // Create a new anonymous post
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody String content) {
        Post newPost = new Post(content);
        Post savedPost = postRepository.save(newPost);
        return ResponseEntity.ok(savedPost);
    }

    // Get all non-expired posts (newest first)
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postRepository.findByExpiresAtAfterOrderByCreatedAtDesc(LocalDateTime.now());
        return ResponseEntity.ok(posts);
    }

    // Get post count
    @GetMapping("/count")
    public ResponseEntity<Long> getPostCount() {
        long count = postRepository.countByExpiresAtAfter(LocalDateTime.now());
        return ResponseEntity.ok(count);
    }

    // Clean up expired posts
    @DeleteMapping("/cleanup")
    public ResponseEntity<String> cleanupExpiredPosts() {
        int deletedCount = postRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        return ResponseEntity.ok("Expired posts cleaned up. Removed: " + deletedCount + " posts");
    }
}
