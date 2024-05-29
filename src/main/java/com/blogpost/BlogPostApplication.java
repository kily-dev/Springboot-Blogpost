package com.blogpost;

import com.blogpost.entities.Post;
import com.blogpost.entities.User;
import com.blogpost.repositories.PostRepository;
import com.blogpost.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootApplication
public class BlogPostApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(BlogPostApplication.class, args);
        PostRepository postRepository=ctx.getBean(PostRepository.class);
        UserRepository userRepository=ctx.getBean(UserRepository.class);
        userRepository.save(new User(0,"ahmed","123","adnane","mortabit","ad@gmail.com","0605508808","la ville kenitra",new ArrayList<>(),new ArrayList<>()));
        userRepository.save(new User());
        postRepository.save(new Post(0,"hhr","hello my friend how are you this the content .",null,null,null,null,null, LocalDateTime.now()));
        userRepository.findAll().forEach(p->System.out.println(p.getFirstName()));

    }

}
