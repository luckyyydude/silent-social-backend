package com.silentsocial.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "resonances")
public class Resonance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private LocalDateTime resonatedAt;

    // No user association - completely anonymous
    // No counter - we'll calculate dynamically

    // Default constructor
    public Resonance() {
    }

    // Constructor
    public Resonance(Post post) {
        this.post = post;
        this.resonatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LocalDateTime getResonatedAt() {
        return resonatedAt;
    }

    public void setResonatedAt(LocalDateTime resonatedAt) {
        this.resonatedAt = resonatedAt;
    }

    @Override
    public String toString() {
        return "Resonance{" +
                "id=" + id +
                ", postId=" + (post != null ? post.getId() : "null") +
                ", resonatedAt=" + resonatedAt +
                '}';
    }
}
