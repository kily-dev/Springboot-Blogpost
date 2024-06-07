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
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    @ManyToOne
    @ToString.Exclude
    private User author;
    @ToString.Exclude
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    @ManyToOne
    @ToString.Exclude
    private Category category;
    private String image;
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> likedBy;
    private Date postedOn;

    public boolean isLikedByUser(User user) {
        return likedBy.contains(user);
    }

}
