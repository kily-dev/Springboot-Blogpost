package com.blogpost.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @NotEmpty(message = "Username not provided.")
    private String username;
    @NotEmpty(message = "Password not provided.")
    private String password;
    @NotEmpty(message = "First name not provided.")
    private String firstName;
    @NotEmpty(message = "Last name not provided.")
    private String lastName;
    @NotEmpty(message = "Email not provided.")
    @Email(message = "Incorrect Email Format.")
    private String email;
    private String phone;
    private String address;
}
