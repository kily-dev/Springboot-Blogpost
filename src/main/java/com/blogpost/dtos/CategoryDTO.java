package com.blogpost.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryDTO {
    @NotEmpty(message = "No Category name provided.")
    private String categoryName;
}
