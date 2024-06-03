package com.blogpost.controllers;

import com.blogpost.entities.User;
import com.blogpost.forms.LoginForm;
import com.blogpost.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /*
    @PostMapping("/login")
    public String authenticateUser(@ModelAttribute LoginForm loginUser) {
        User user = userRepository.findByUsername(loginUser.getUsername());

        if (user != null && passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            // Authentication successful
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/index"; // Redirect to dashboard or any authenticated page
        } else {
            // Authentication failed
            return "redirect:/login?error"; // Redirect to login page with error parameter
        }
    }*/
}
