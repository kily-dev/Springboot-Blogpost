package com.blogpost.services;

import com.blogpost.dtos.CommentDTO;
import com.blogpost.entities.Comment;
import com.blogpost.entities.Post;
import com.blogpost.entities.User;
import com.blogpost.repositories.CommentRepository;
import com.blogpost.repositories.PostRepository;
import com.blogpost.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment addComment(int postId, CommentDTO commentDTO, String username) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        User user = userRepository.findByUsername(username).get();

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(user);
        comment.setContent(commentDTO.getContent());
        comment.setCreationDate(LocalDateTime.now());
        post.getComments().add(comment);


        return commentRepository.save(comment);
    }

    public void deleteComment(int commentId, String username) {
        User user = userRepository.findByUsername(username).get();
        Comment comment = commentRepository.findById(commentId).get();
        if(user.getId()==comment.getAuthor().getId()){
            commentRepository.deleteById(commentId);
        }

    }
}
