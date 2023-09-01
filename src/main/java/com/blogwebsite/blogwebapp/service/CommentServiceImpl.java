package com.blogwebsite.blogwebapp.service;

import com.blogwebsite.blogwebapp.model.Comment;
import com.blogwebsite.blogwebapp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Comment findCommentById(int id) {
        return commentRepository.findCommentById(id);
    }

    @Override
    public void deleteCommentById(int id) {
        Comment comment = commentRepository.findCommentById(id);
        commentRepository.delete(comment);
    }
}
