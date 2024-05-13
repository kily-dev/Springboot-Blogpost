package com.blogpost.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue
    private int id;
    @OneToOne
    private Post post;
    @OneToOne
    private User author;
    private String content;
    private Date creationDate;
}
