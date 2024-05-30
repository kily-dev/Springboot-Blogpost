package com.blogpost.Controllers;

import com.blogpost.entities.Post;
import com.blogpost.repositories.PostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @RequestMapping(value = "/index" )
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "0")int p,
                        @RequestParam(name="size",defaultValue = "3")int s,
                        @RequestParam(name = "motcle",defaultValue = "")String mc){

        Page<Post> pagePosts = postRepository.chercher("%"+mc+"%",PageRequest.of(p,s,Sort.unsorted()));
        model.addAttribute("listPosts",pagePosts.getContent());
        int[] pages=new int[pagePosts.getTotalPages()];
        model.addAttribute("pages",pages);
        model.addAttribute("size",s);
        model.addAttribute("pagesCourante",p);
        model.addAttribute("motcle",mc);

        return  "post";
    }
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public String delete(int id,String motcle,int page,int size){
        postRepository.deleteById(id);
        return "redirect:/index?page="+page+"&size="+size+"&motcle"+motcle;
    }

    @RequestMapping(value="/form", method=RequestMethod.GET)
    public String formPost(Model model){
        model.addAttribute("post", new Post());
        return "formPost";
    }
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public String save(Model model, @Valid Post post, BindingResult bindingResult){

        if(bindingResult.hasErrors())
            return "formPost";
        postRepository.save(post);
        return "addpost";
    }

   /* @RequestMapping(value="/edit", method=RequestMethod.GET)
    public String editer(Model model, int id){
            Optional<Post> p = postRepository.findById(id);
        model.addAttribute("produit", p);
        return "editProduit";
    } */

    @RequestMapping(value="/tailwind")
    public String tailwind(Model model){
        return "homepage";
    }

}

