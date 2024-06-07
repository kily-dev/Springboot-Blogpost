package com.blogpost.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Category {
    @Id
    private String categoryName;
    @ToString.Exclude
    @OneToMany(mappedBy = "category")
    private List<Post> posts;
}
