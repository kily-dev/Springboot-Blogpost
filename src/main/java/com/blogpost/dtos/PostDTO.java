package com.blogpost.dtos;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;



@Data
public class PostDTO {
    @NotEmpty(message="Title is required.")
    private String title;
    @NotEmpty(message="Content is required.")
    @Size(min = 300, message = "Content should be atleast 300 characters.")
    @Lob
    private String content;
    private String category;
    private MultipartFile image;
}
