package com.blogwebsite.blogwebapp.repository;

import com.blogwebsite.blogwebapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Comment save(Comment comment);

    Comment findCommentById(int id);

    void delete(Comment comment);
}
