package com.blogpost.dtos;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentDTO {
    @NotEmpty(message = "Comments cannot be empty.")
    private String content;
}
