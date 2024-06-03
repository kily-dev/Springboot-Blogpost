package com.blogpost.controllers;

import com.blogpost.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LikeController {

    @Autowired
    private PostService postService;

    @GetMapping("/like/{postId}")
    public String likePost(@PathVariable int postId, Authentication authentication) {
        String username = authentication.getName();
        postService.likePost(postId, username);
        return "redirect:/post/" + postId;
    }

    @GetMapping("/unlike/{postId}")
    public String unlikePost(@PathVariable int postId, Authentication authentication) {
        String username = authentication.getName();
        postService.unlikePost(postId, username);
        return "redirect:/post/" + postId;
    }
}
