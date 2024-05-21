package com.blogpost.Controllers;

import com.blogpost.entities.Post;
import com.blogpost.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @RequestMapping(value = "/index" )
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "0")int p,
                        @RequestParam(name="size",defaultValue = "3")int s){

        Page<Post> pagePosts = postRepository.findAll(PageRequest.of(p,s,Sort.unsorted()));
        model.addAttribute("listPosts",pagePosts.getContent());
        int[] pages=new int[pagePosts.getTotalPages()];
        model.addAttribute("pages",pages);
        return  "post";
    }
}
