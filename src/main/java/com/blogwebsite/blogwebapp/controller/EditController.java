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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/edit")
public class EditController {
    final int PAGE_LIMIT = 10;
    final int PAGE_STARTING = 0;
    private final PostService postService;
    private final CommentService commentService;
    private final TagService tagService;

    @Autowired
    public EditController(PostService postService, CommentService commentService, TagService tagService) {
        this.postService = postService;
        this.commentService = commentService;
        this.tagService = tagService;

    }

    @PostMapping("/blog-post")
    public String updateBlogPost(@ModelAttribute("post_data") DummyPosts theDummyPosts, @RequestParam(name = "selectedTags", required = false) Integer[] selectedTags, @RequestParam(name = "userRole") String userRole, Model model) {
        int postId = theDummyPosts.getId();
        Posts post = postService.findPostById(postId);

        if (userRole.equals("ROLE_ADMIN")) {
            String authorNameByAdmin = "ADMIN " + theDummyPosts.getAuthor();
            post.setAuthor(authorNameByAdmin);
        } else {
            post.setAuthor(theDummyPosts.getAuthor());
        }

        post.setTitle(theDummyPosts.getTitle());
        post.setExcerpt(theDummyPosts.getExcerpt());
        post.setContent(theDummyPosts.getContent());
        String[] newTags = theDummyPosts.getTags().split(",");
        post.getTags().clear();

        if (selectedTags != null) {

            for (Integer tagId : selectedTags) {
                Tags tag = tagService.findById(tagId);
                post.addTag(tag);
            }
        }

        for (String eachTag : newTags) {

            if (tagService.isExistsByName(eachTag)) {
                Tags tag = tagService.findByName(eachTag);
                post.addTag(tag);
            } else {
                Tags tag = new Tags(eachTag);
                tagService.save(tag);
                post.addTag(tag);
            }

        }

        post.setUpdated_at(LocalDateTime.now());
        postService.updateCompletePost(post);
        model.addAttribute("post", post);

        return "viewBlogPost";
    }

    @PostMapping("/blog-post-comment")
    public String updateBlogPostComment(@ModelAttribute("comment_data") Comment editComment, @ModelAttribute("postId") int thePostId, Model model) {
        Comment comment = commentService.findCommentById(editComment.getId());
        comment.setName(editComment.getName());
        comment.setEmail(editComment.getEmail());
        comment.setComment(editComment.getComment());
        comment.setUpdated_at(LocalDateTime.now());
        commentService.updateComment(comment);
        Posts thePost = postService.findPostById(thePostId);
        List<Comment> theCommentList = thePost.getComment();
        model.addAttribute("post", thePost);
        model.addAttribute("commentList", theCommentList);
        model.addAttribute("postId2", 0);

        return "viewBlogPost";
    }

    @GetMapping("/blog-post-published/{postId}")
    public String updateBlogPostPublished(@PathVariable("postId") int postId, Model model) {
        Posts post = postService.findPostById(postId);
        post.setIs_published(true);
        post.setPublished_at(LocalDateTime.now());
        postService.updateCompletePost(post);
        List<Tags> tagsList = tagService.findAll();
        List<Posts> postData = postService.findOnlyTenPost(PAGE_LIMIT, PAGE_STARTING);
        List<String> theAllUniqueAuthorsName = postService.findAllUniqueAuthorsName();
        model.addAttribute("post", post);
        model.addAttribute("page", PAGE_STARTING);
        model.addAttribute("postData", postData);
        model.addAttribute("tagsList", tagsList);
        model.addAttribute("allUniqueAuthorsName", theAllUniqueAuthorsName);

        return "home";
    }

    @PostMapping("/blog-post-draft")
    public String updateBlogDraftPost(@ModelAttribute("post_data") DummyPosts dummyPost, @RequestParam(name = "selectedTags", required = false) Integer[] selectedTags, Model model) {
        int postId = dummyPost.getId();
        Posts post = postService.findPostById(postId);
        post.setTitle(dummyPost.getTitle());
        post.setAuthor(dummyPost.getAuthor());
        post.setExcerpt(dummyPost.getExcerpt());
        post.setContent(dummyPost.getContent());
        String[] newTags = dummyPost.getTags().split(",");
        String authorName = dummyPost.getAuthor();
        post.getTags().clear();

        if (selectedTags != null) {

            for (Integer tagId : selectedTags) {
                Tags tag = tagService.findById(tagId);
                post.addTag(tag);
            }

        }

        for (String eachTag : newTags) {

            if (tagService.isExistsByName(eachTag)) {
                Tags tag = tagService.findByName(eachTag);
                post.addTag(tag);
            } else {
                Tags tag = new Tags(eachTag);
                tagService.save(tag);
                post.addTag(tag);
            }

        }

        post.setUpdated_at(LocalDateTime.now());
        postService.updateCompletePost(post);
        List<Posts> draftPosts = postService.findAllDraftPost(authorName);
        model.addAttribute("postData", draftPosts);

        return "draft";
    }
}
