package com.blogpost.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;
    @OneToOne
    private User author;
    @OneToMany
    private List<Comment> comments;
    @ManyToOne
    private Category category;
    private String imageUrl;
    @OneToMany
    private List<User> likedBy;
    private LocalDateTime postedOn;

}
