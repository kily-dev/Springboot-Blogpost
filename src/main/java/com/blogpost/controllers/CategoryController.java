package com.blogpost.controllers;

import com.blogpost.dtos.CategoryDTO;
import com.blogpost.entities.Category;
import com.blogpost.entities.Post;
import com.blogpost.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{categoryName}/posts")
    public String getPostsByCategory(@PathVariable String categoryName, Model model,
                                     @RequestParam(name = "page", defaultValue = "0") int p) {
        Page<Post> pagePosts = categoryService.getPostsByCategory(categoryName, p, 10);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("posts", pagePosts.getContent());
        int[] pages = new int[pagePosts.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("size", 10);
        model.addAttribute("pagesCourante", p);
        model.addAttribute("here",categoryName);
        return "postsByCategory";
    }


    @GetMapping("/crud")
    public String getAllCategoriesForCRUD(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories1";
    }

    @GetMapping
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/create")
    public String showCreateCategoryForm(Model model) {
        model.addAttribute("categoryDTO", new CategoryDTO());
        return "createCategory";
    }

    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute CategoryDTO categoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "createCategory";
        }
        categoryService.addCategory(categoryDTO);
        return "redirect:/categories/crud";
    }

    @GetMapping("/edit/{categoryName}")
    public String showEditCategoryForm(@PathVariable String categoryName, Model model) {
        Category category = categoryService.getCategoryByName(categoryName);
        if (category == null) {
            return "redirect:/categories/crud";
        }
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName(category.getCategoryName());
        model.addAttribute("categoryDTO", categoryDTO);
        return "editCategory";
    }

    @PostMapping("/edit/{categoryName}")
    public String editCategory(@PathVariable String categoryName, @Valid @ModelAttribute CategoryDTO categoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "editCategory";
        }
        categoryService.editCategory(categoryName, categoryDTO);
        return "redirect:/categories/crud";
    }

    @GetMapping("/delete/{categoryName}")
    public String deleteCategory(@PathVariable String categoryName) {
        categoryService.deleteCategory(categoryName);
        return "redirect:/categories/crud";
    }
}
