package com.blogpost.repositories;

import com.blogpost.entities.Category;
import com.blogpost.entities.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {

}
