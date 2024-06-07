package com.blogpost.repositories;

import com.blogpost.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByCategoryName(String categoryName);
    void deleteByCategoryName(String categoryName);
}
