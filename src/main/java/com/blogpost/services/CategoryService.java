package com.blogpost.services;

import com.blogpost.dtos.CategoryDTO;
import com.blogpost.entities.Category;
import com.blogpost.entities.Post;
import com.blogpost.repositories.CategoryRepository;
import com.blogpost.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;


    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    public void addCategory(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getCategoryName(), null);
        categoryRepository.save(category);
    }

    public void editCategory(String categoryName, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        if (category != null) {
            category.setCategoryName(categoryDTO.getCategoryName());
            categoryRepository.save(category);
        }
    }


    @Transactional
    public void deleteCategory(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        if (category != null) {
            List<Post> posts = category.getPosts();
            for (Post post : posts) {
                post.setCategory(null);
            }
            categoryRepository.deleteByCategoryName(categoryName);
        }
    }

    public Page<Post> getPostsByCategory(String categoryName, int page, int size) {
        return postRepository.findByCategoryName(categoryName, PageRequest.of(page, size));
    }
}
