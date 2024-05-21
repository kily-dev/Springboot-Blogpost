package com.blogpost.repositories;

import com.blogpost.entities.Category;
import com.blogpost.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
