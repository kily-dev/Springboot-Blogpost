package com.blogpost.services;

import com.blogpost.entities.Post;
import com.blogpost.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post savePostToDB(MultipartFile file, String title, String content) {
        Post p = new Post();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new IllegalArgumentException("Not a valid file path: " + fileName);
        }
        try {
            p.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.setTitle(title);
        p.setContent(content);

        return postRepository.save(p);
    }
}
