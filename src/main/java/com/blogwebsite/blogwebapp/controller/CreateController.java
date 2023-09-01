package com.blogwebsite.blogwebapp.controller;

import com.blogwebsite.blogwebapp.model.*;
import com.blogwebsite.blogwebapp.service.PostService;
import com.blogwebsite.blogwebapp.service.TagService;
import com.blogwebsite.blogwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/create")
public class CreateController {
    final int PAGE_LIMIT = 10;
    final int PAGE_STARTING = 0;
    private final PostService postService;
    private final TagService tagService;
    private final UserService userService;

    @Autowired
    public CreateController(PostService postService, TagService tagService, UserService userService) {
        this.postService = postService;
        this.tagService = tagService;
        this.userService = userService;
    }

    @GetMapping("/create-page")
    public String createBlog(Model model) {
        model.addAttribute("tagList", tagService.findAll());
        model.addAttribute("post_data", new Posts());

        return "createBlog";
    }

    @PostMapping("/process-blog-post")
    public String createdBlogPost(@ModelAttribute("post_data") DummyPosts dummyPosts, @RequestParam(name = "selectedTags", required = false) Integer[] selectedTags, @RequestParam(name = "userRole") String userRole, Model model) {
        Posts post = new Posts();
        post.setTitle(dummyPosts.getTitle());
        post.setExcerpt(dummyPosts.getExcerpt());
        post.setContent(dummyPosts.getContent());

        if (userRole.equals("[ROLE_ADMIN]")) {
            String authorNameByAdmin = "  " + dummyPosts.getAuthor();
            post.setAuthor(authorNameByAdmin);
        } else {
            post.setAuthor(dummyPosts.getAuthor());
        }

        post.setIs_published(dummyPosts.getIs_published());
        post.setCreated_at(LocalDateTime.now());
        String[] newTags = dummyPosts.getTags().split(",");
        boolean isPublished = dummyPosts.getIs_published();

        if (isPublished) {
            post.setPublished_at(LocalDateTime.now());
        }

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

        postService.save(post);
        List<Posts> onlyTenPost = postService.findOnlyTenPost(PAGE_LIMIT, PAGE_STARTING);
        model.addAttribute("postData", onlyTenPost);

        return "redirect:/";
    }

    @PostMapping("/blog-post-comment")
    public String createBlogPostComment(@ModelAttribute("comment_data") Comment comment, @ModelAttribute(name = "postId") int postId, Model theModel) {
        Comment userComment = new Comment(comment.getName(), comment.getEmail(), comment.getComment());
        userComment.setCreated_at(LocalDateTime.now());
        postService.updatePostWithComment(postId, userComment);
        Posts thePost = postService.findPostById(postId);
        theModel.addAttribute("post", thePost);
        theModel.addAttribute("commentList", thePost.getComment());
        theModel.addAttribute("comment_data", new Comment());

        return "viewBlogPost";
    }

    @PostMapping("/create-new-user")
    public String createNewUser(@ModelAttribute("user") User theUser, Model theModel) {
        String userName = theUser.getUsername();

        if (userService.existsByName(userName)) {
            theModel.addAttribute("error", "User already exists!");
            theModel.addAttribute("user", new User());
            return "newUser";
        }

        String password = theUser.getPassword();
        password = "{noop}" + password;
        theUser.setPassword(password);
        userService.save(theUser);

        return "redirect:/";
    }

}
