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
    @ManyToOne
    private User author;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    @ManyToOne
    private Category category;
    private String image;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> likedBy;
    private Date postedOn;

    public boolean isLikedByUser(User user) {
        return likedBy.contains(user);
    }

}
