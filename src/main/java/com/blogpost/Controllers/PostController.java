package com.blogpost.Controllers;

import com.blogpost.dtos.PostDTO;
import com.blogpost.entities.Post;
import com.blogpost.repositories.PostRepository;
import com.blogpost.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Optional;


@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

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
        PostDTO postDTO = new PostDTO();
        model.addAttribute("postDTO",  postDTO);
        return "formPost";
    }
    @RequestMapping(value="/form", method=RequestMethod.POST)
    public String save(@ModelAttribute @Valid PostDTO postdto, BindingResult bindingResult){

        if(bindingResult.hasErrors())
            return "formPost";


        MultipartFile image = postdto.getImage();
        Date postedOn = new Date();
        String storageFileName = postedOn.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        Post post = new Post();
        post.setTitle(postdto.getTitle());
        post.setContent(postdto.getContent());
        post.setImage(storageFileName);
        post.setPostedOn(postedOn);
        postRepository.save(post);


        return "redirect:/index";
    }



    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public String editer(Model model, int id){
        Optional<Post> p = postRepository.findById(id);
        model.addAttribute("post", p.get());
        return "editProduit";
    }

    @RequestMapping(value="/")
    public String home(){
        return "redirect:/index";
    }


}

