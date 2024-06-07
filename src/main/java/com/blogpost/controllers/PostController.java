package com.blogpost.controllers;

import com.blogpost.dtos.CommentDTO;
import com.blogpost.dtos.PostDTO;
import com.blogpost.entities.Post;
import com.blogpost.entities.User;
import com.blogpost.repositories.UserRepository;
import com.blogpost.services.CategoryService;
import com.blogpost.services.CommentService;
import com.blogpost.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/post/{id}")
    public String getPostById(@PathVariable int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User currentUser = userRepository.findByUsername(username).get();
        Optional<Post> postOptional = postService.getPostById(id);
        if (postOptional.isPresent()) {
            model.addAttribute("post", postOptional.get());
            model.addAttribute("commentDTO", new CommentDTO());
            model.addAttribute("comments", postOptional.get().getComments());
            model.addAttribute("currentUser", currentUser);
            return "blog";
        } else {
            return "redirect:/index";
        }
    }







    @RequestMapping(value = "/blogs")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int p,
                        @RequestParam(name = "size", defaultValue = "3") int s,
                        @RequestParam(name = "motcle", defaultValue = "") String mc) {

        Page<Post> pagePosts = postService.searchPosts(mc, p, s);
        model.addAttribute("listPosts", pagePosts.getContent());
        int[] pages = new int[pagePosts.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("size", s);
        model.addAttribute("pagesCourante", p);
        model.addAttribute("motcle", mc);

        return "post";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(int id) {
        postService.deletePost(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String formPost(Model model) {
        PostDTO postDTO = new PostDTO();
        model.addAttribute("postDTO", postDTO);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "formPost";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String save(@ModelAttribute @Valid PostDTO postDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "formPost";
        }

        try {
            postService.savePost(postDTO);
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "formPost";
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model, @RequestParam int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> currentUser = userRepository.findByUsername(username);
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            if(post.get().getAuthor()!=currentUser.get()){
                return "redirect:/index";
            }
            model.addAttribute("post", post.get());

            PostDTO postDTO = new PostDTO();
            postDTO.setTitle(post.get().getTitle());
            postDTO.setContent(post.get().getContent());

            model.addAttribute("postDTO", postDTO);
        }
        return "editProduit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String update(Model model, @RequestParam int id, @Valid @ModelAttribute PostDTO postDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "editProduit";
        }

        try {
            postService.updatePost(id, postDTO);
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "editProduit";
        }

        return "redirect:/index";
    }

    @RequestMapping(value = "/")
    public String home(Model model) {
        int p = 0, s = 10;
        Page<Post> latestPosts = postService.getLatestPosts(p, s);
        model.addAttribute("posts", latestPosts.getContent());
        int[] pages = new int[latestPosts.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("size", s);
        model.addAttribute("pagesCourante", p);


        return "homepage";
    }

    @RequestMapping(value = "/blog")
    public String home(Model model,
                       @RequestParam(name = "page", defaultValue = "0") int p) {
        int s = 10;
        Page<Post> latestPosts = postService.getLatestPosts(p, s);
        model.addAttribute("posts", latestPosts.getContent());
        int[] pages = new int[latestPosts.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("size", s);
        model.addAttribute("pagesCourante", p);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("here","All Posts");
        return "postsByCategory";
    }

    @RequestMapping(value = "/latest", method = RequestMethod.GET)
    public String latestPosts(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int p,
                              @RequestParam(name = "size", defaultValue = "3") int s) {
        Page<Post> latestPosts = postService.getLatestPosts(p, s);
        model.addAttribute("listPosts", latestPosts.getContent());
        int[] pages = new int[latestPosts.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("size", s);
        model.addAttribute("pagesCourante", p);

        return "post";
    }

    @RequestMapping(value = "/popular", method = RequestMethod.GET)
    public String popularPosts(Model model,
                               @RequestParam(name = "page", defaultValue = "0") int p,
                               @RequestParam(name = "size", defaultValue = "3") int s) {
        Page<Post> popularPosts = postService.getPopularPosts(p, s);
        model.addAttribute("listPosts", popularPosts.getContent());
        int[] pages = new int[popularPosts.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("size", s);
        model.addAttribute("pagesCourante", p);

        return "post";
    }

    @RequestMapping(value = "/tailwind")
    public String tailwind(Model model){
        return "postsByCategory";
    }

    @RequestMapping("/about")
    public String about(){
        return "about";
    }
}

