package com.blogwebsite.blogwebapp.controller;

import com.blogwebsite.blogwebapp.model.Posts;
import com.blogwebsite.blogwebapp.service.CommentService;
import com.blogwebsite.blogwebapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/delete")
public class DeleteController {
    final int PAGE_LIMIT = 10;
    final int PAGE_STARTING = 0;
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public DeleteController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/blog-post/{postId}")
    public String deleteBlogPost(@PathVariable(name = "postId") int postId, Model model) {
        postService.deletePostById(postId);
        List<Posts> postsList = postService.findOnlyTenPost(PAGE_LIMIT, PAGE_STARTING);
        model.addAttribute("postData", postsList);

        return "redirect:/";
    }

    @GetMapping("/blog-post-draft/{postId}")
    public String deleteBlogPostDraft(@PathVariable(name = "postId", required = false) int postId, @RequestParam(name = "authorName", required = false) String authorName, Model model) {
        postService.deletePostById(postId);
        List<Posts> postsList = postService.findAllDraftPost(authorName);
        model.addAttribute("postData", postsList);

        return "draft";
    }

    @GetMapping("/blog-post-comment/{postId}/{commentId}")
    public String deleteBlogPostComment(@PathVariable("commentId") int commentId, @PathVariable("postId") int postId, Model model) {
        commentService.deleteCommentById(commentId);
        Posts post = postService.findPostById(postId);
        model.addAttribute("post", post);
        model.addAttribute("commentList", post.getComment());

        return "viewBlogPost";
    }

}
