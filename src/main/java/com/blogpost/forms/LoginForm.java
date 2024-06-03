package com.blogpost.forms;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginForm {
    @NotEmpty(message = "Username not provided.")
    private String username;
    @NotEmpty(message = "Password not provided.")
    private String password;
}