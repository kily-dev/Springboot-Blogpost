package com.blogpost.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @ToString.Exclude
    private Post post;
    @ManyToOne
    @ToString.Exclude
    private User author;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private LocalDateTime creationDate;
}
