package com.blogpost.services;

import com.blogpost.dtos.PostDTO;
import com.blogpost.entities.Post;
import com.blogpost.entities.User;
import com.blogpost.repositories.PostRepository;
import com.blogpost.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public void likePost(int postId, String username) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            User user = userRepository.findByUsername(username).get();
            if (!post.getLikedBy().contains(user)) {
                post.getLikedBy().add(user);
                postRepository.save(post);
            }
        }
    }

    public void unlikePost(int postId, String username) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            User user = userRepository.findByUsername(username).get();
            post.getLikedBy().remove(user);
            postRepository.save(post);
        }
    }

    public Page<Post> searchPosts(String keyword, int page, int size) {
        return postRepository.chercher("%" + keyword + "%", PageRequest.of(page, size));
    }

    public void deletePost(int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Retrieve the corresponding User entity from the database
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Post> postToBeDeleted = postRepository.findById(id);
        if (postToBeDeleted.isPresent()) {
            if (postToBeDeleted.get().getAuthor() == user.get()) {
                postRepository.deleteById(id);
            }
        }


    }

    public Post savePost(PostDTO postDTO) throws IOException {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Retrieve the corresponding User entity from the database
        Optional<User> user = userRepository.findByUsername(username);

        System.out.println(user.get());

        MultipartFile image = postDTO.getImage();
        Date postedOn = new Date();
        String storageFileName = postedOn.getTime() + "_" + image.getOriginalFilename();

        String uploadDir = "public/images/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = image.getInputStream()) {
            Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
        }

        // Create a new Post entity
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImage(storageFileName);
        post.setPostedOn(postedOn);

        // Set the current user as the author of the post
        post.setAuthor(user.get());

        return postRepository.save(post);
    }

    public Post updatePost(int id, PostDTO postDTO) throws IOException {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if (!postDTO.getImage().isEmpty()) {
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + post.getImage());
                Files.delete(oldImagePath);

                MultipartFile image = postDTO.getImage();
                Date postedOn = new Date();
                String storageFileName = postedOn.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                }

                post.setImage(storageFileName);
            }

            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());

            return postRepository.save(post);
        } else {
            throw new IOException("Post not found");
        }
    }

    public Optional<Post> getPostById(int id) {
        return postRepository.findById(id);
    }

    public Page<Post> getLatestPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findLatestPosts(pageable);
    }

    public Page<Post> getPopularPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findPopularPosts(pageable);
    }
}
