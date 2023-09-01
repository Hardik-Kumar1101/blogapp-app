package com.blogwebsite.blogwebapp.service;

import com.blogwebsite.blogwebapp.model.Comment;

public interface CommentService {

    void updateComment(Comment comment);

    Comment findCommentById(int id);

    void deleteCommentById(int id);

}
