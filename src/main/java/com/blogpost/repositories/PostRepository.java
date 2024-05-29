package com.blogpost.repositories;

import com.blogpost.entities.Category;
import com.blogpost.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("select p from Post p where p.title like :x")
    public Page<Post> chercher(@Param("x") String mc, Pageable pageable);

}
