package com.blogpost.Controllers;

import com.blogpost.dtos.PostDTO;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        //post.setPostedOn(postedOn);
        postRepository.save(post);


        return "redirect:/index";
    }



    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public String editer(Model model,@RequestParam int id){
        Post p = postRepository.findById(id).get();
        model.addAttribute("post", p);

        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(p.getTitle());
        postDTO.setContent(p.getContent());

        model.addAttribute("postDTO",postDTO);


        return "editProduit";
    }

    @RequestMapping(value="/edit", method=RequestMethod.POST)
    public String update(Model model,@RequestParam int id ,@Valid @ModelAttribute
    PostDTO postDTO, BindingResult result) {
        Post p = postRepository.findById(id).get();
        model.addAttribute("post", p);
        if (result.hasErrors()) {
            return "editProduit";
        }

        if (!postDTO.getImage().isEmpty()) {
            // delete old image
            String uploadDir = "public/images/";
            Path oldImagePath = Paths.get(uploadDir + p.getImage());

            try {
                Files.delete(oldImagePath);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }

            // save new image file
            MultipartFile image = postDTO.getImage();
            Date postedOn = new Date();
            String storageFileName = postedOn.getTime() + "_" + image.getOriginalFilename();

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            } catch(IOException ex){
                System.out.println("Exeption:" + ex.getMessage());
            }

            p.setImage(storageFileName);

            p.setTitle(postDTO.getTitle());
            p.setContent(postDTO.getContent());

            postRepository.save(p);


        }


        return "redirect:/index";
    }



    @RequestMapping(value="/")
    public String home(){
        return "redirect:/index";
    }


}

