package com.blogwebsite.blogwebapp.rest;


import com.blogwebsite.blogwebapp.model.Posts;
import com.blogwebsite.blogwebapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostRestController {
    private final PostService postService;

    @Autowired
    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<Posts> getPosts() {
        return postService.findAll();
    }

    @GetMapping("/posts/{id}")
    public Posts getPostById(@PathVariable int id) {
        Posts post = postService.findPostById(id);

        if (post != null) {
            return post;
        } else {
            throw new RuntimeException("Post not found");
        }

    }

    @PostMapping("/posts")
    public Posts savePost(@RequestBody Posts posts) {
        Posts post = postService.saveReturnObject(posts);
        return post;
    }

    @PutMapping("/posts/{id}")
    public Posts updatePost(@PathVariable int id, @RequestBody Posts posts) {
        Posts post = postService.findPostById(id);

        if (post == null) {
            throw new RuntimeException("Post not found");
        }

        posts.setId(id);
        postService.updateCompletePost(posts);
        Posts updatedPost = postService.findPostById(id);

        return updatedPost;
    }

    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable int id) {
        Posts post = postService.findPostById(id);

        if (post == null) {
            throw new RuntimeException("Post not found");
        }

        postService.deletePostById(id);

        return "Post deleted id: " + id;
    }

    @GetMapping("/posts/next/{page}")
    public List<Posts> nextPage(@PathVariable(name = "page", required = false) int page) {
        List<Posts> postsList = postService.findOnlyTenPost(10, page * 10);
        return postsList;
    }

    @GetMapping("/posts/previous/{page}")
    public List<Posts> previousPage(@PathVariable(name = "page", required = false) int page) {
        List<Posts> postsList = new ArrayList<>();

        if (page < 0) {
            throw new RuntimeException("Page cannot be less than 0");
        } else {
            postsList = postService.findOnlyTenPost(10, page / 10);
        }

        return postsList;
    }

    @GetMapping("/posts/search/{search}")
    public List<Posts> search(@PathVariable String search) {
        List<Posts> postsList = new ArrayList<>();

        if (search.isEmpty()) {
            throw new RuntimeException("Search field cannot be empty");
        } else {
            postsList = postService.findPostByTagTitleContentAuthorExcerpt(search);
        }

        return postsList;
    }

    @GetMapping("/posts/sortField={sortField}&order={order}")
    public List<Posts> filterForTags(@RequestParam(name = "sortField", required = false) String sortField, @RequestParam(name = "order", required = false) String order) {
        System.out.println(sortField);
        System.out.println(order);
        return null;
    }
}
