package com.silentsocial.backend.controller;

import com.silentsocial.backend.model.Resonance;
import com.silentsocial.backend.model.Post;
import com.silentsocial.backend.repository.ResonanceRepository;
import com.silentsocial.backend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/resonances")
public class ResonanceController {

    @Autowired
    private ResonanceRepository resonanceRepository;

    @Autowired
    private PostRepository postRepository;

    // Add a resonance to a post
    @PostMapping("/post/{postId}")
    public ResponseEntity<String> resonateWithPost(@PathVariable UUID postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        
        if (postOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post post = postOptional.get();
        
        // Check if post is expired
        if (post.isExpired()) {
            return ResponseEntity.badRequest().body("Cannot resonate with expired post");
        }

        // Create and save the resonance
        Resonance resonance = new Resonance(post);
        resonanceRepository.save(resonance);

        return ResponseEntity.ok("Resonance added silently");
    }

    // Get resonance count for a post
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Long> getResonanceCount(@PathVariable UUID postId) {
        long count = resonanceRepository.countByPostId(postId);
        return ResponseEntity.ok(count);
    }

    // Check if a post has resonances
    @GetMapping("/post/{postId}/exists")
    public ResponseEntity<Boolean> hasResonances(@PathVariable UUID postId) {
        boolean hasResonances = resonanceRepository.existsByPostId(postId);
        return ResponseEntity.ok(hasResonances);
    }
}
