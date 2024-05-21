package com.blogpost.repositories;

import com.blogpost.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
