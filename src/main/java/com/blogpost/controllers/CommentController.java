package com.blogpost.controllers;

import com.blogpost.dtos.CommentDTO;
import com.blogpost.entities.Comment;
import com.blogpost.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add/{postId}")
    public String addComment(@PathVariable int postId, @ModelAttribute CommentDTO commentDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        commentService.addComment(postId, commentDTO, username);
        return "redirect:/post/" + postId;
    }

    @PostMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable int commentId, @RequestParam int postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        commentService.deleteComment(commentId, username);
        return "redirect:/post/" + postId;
    }
}
