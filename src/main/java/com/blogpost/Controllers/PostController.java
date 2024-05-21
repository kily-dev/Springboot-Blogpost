package com.blogpost.Controllers;

import com.blogpost.entities.Post;
import com.blogpost.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @RequestMapping(value = "/index")
    public String index(Model model){
        //List<Post> posts=PostRepository.findAll();
        return  "post";
    }
}
