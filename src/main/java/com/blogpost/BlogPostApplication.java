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
        userRepository.findAll().forEach(p->System.out.println(p.getFirstName()));

    }

}
