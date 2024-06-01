package com.blogpost.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
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
    @NonNull
    private String title;
    @NonNull
    private String content;
    @OneToOne
    private User author;
    @OneToMany
    private List<Comment> comments;
    @ManyToOne
    private Category category;
    private String image;
    @OneToMany
    private List<User> likedBy;
    private Date postedOn;

}
