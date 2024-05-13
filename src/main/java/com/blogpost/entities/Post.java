package com.blogpost.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {
    @Id
    @GeneratedValue
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
