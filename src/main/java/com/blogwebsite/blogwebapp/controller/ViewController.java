package com.blogwebsite.blogwebapp.controller;

import com.blogwebsite.blogwebapp.model.Comment;
import com.blogwebsite.blogwebapp.model.DummyPosts;
import com.blogwebsite.blogwebapp.model.Posts;
import com.blogwebsite.blogwebapp.model.Tags;
import com.blogwebsite.blogwebapp.service.CommentService;
import com.blogwebsite.blogwebapp.service.PostService;
import com.blogwebsite.blogwebapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/view")
public class ViewController {
    final int PAGE_LIMIT = 10;
    final int PAGE_STARTING = 0;
    private final PostService postService;
    private final CommentService commentService;
    private final TagService tagService;

    @Autowired
    public ViewController(PostService postService, CommentService commentService, TagService tagService) {
        this.postService = postService;
        this.commentService = commentService;
        this.tagService = tagService;

    }

    @GetMapping("/blog-post/{postId}")
    public String viewBlogPost(@PathVariable(name = "postId") int postId, Model model) {
        Posts post = postService.findPostById(postId);
        List<Comment> commentList = post.getComment();

        if (commentList.isEmpty()) {
            commentList = new ArrayList<>();
        }

        model.addAttribute("post", post);
        model.addAttribute("postId", postId);
        model.addAttribute("commentList", commentList);
        model.addAttribute("comment_data", new Comment());

        return "viewBlogPost";
    }

    @GetMapping("/blog-post-sort-by-published")
    public String viewBlogPostSortByPublished(Model model) {
        List<Tags> tagList = tagService.findAll();
        List<String> allUniqueAuthorsName = postService.findAllUniqueAuthorsName();
        List<Posts> postList = postService.findSortedByPublishedASC(PAGE_LIMIT, PAGE_STARTING);
        model.addAttribute("page", PAGE_STARTING);
        model.addAttribute("postData", postList);
        model.addAttribute("tagsList", tagList);
        model.addAttribute("allUniqueAuthorsName", allUniqueAuthorsName);

        return "home";
    }

    @GetMapping("/blog-post-edit-page/{postId}")
    public String updateBlogPost(@PathVariable(name = "postId") int thePostId, Model model) {
        Posts post = postService.findPostById(thePostId);
        List<Tags> tagList = tagService.findAll();
        model.addAttribute("post", post);
        model.addAttribute("tagList", tagList);
        model.addAttribute("post_data", new DummyPosts());

        return "updateBlog";
    }

    @GetMapping("/blog-post-edit-page-draft/{postId}")
    public String updateBlogPostDraft(@PathVariable(name = "postId") int thePostId, Model model) {
        Posts post = postService.findPostById(thePostId);
        List<Tags> tagList = tagService.findAll();
        model.addAttribute("post", post);
        model.addAttribute("tagList", tagList);

        return "updateDraft";
    }

    @GetMapping("/blog-post-comment/{postId}")
    public String addComment(@PathVariable(name = "postId") int postId, Model model) {
        model.addAttribute("postId", postId);
        model.addAttribute("comment_data", new Comment());

        return "commentOnPost";
    }

    @GetMapping("/blog-post-comment-edit-page/{postId}/{commentId}")
    public String updateBlogPostComment(@PathVariable(name = "commentId") int commentId, @PathVariable(name = "postId") int postId, Model model) {
        Comment comment = commentService.findCommentById(commentId);
        model.addAttribute("postId", postId);
        model.addAttribute("commentData", comment);
        model.addAttribute("comment_data", new Comment());

        return "updateComment";
    }

}
