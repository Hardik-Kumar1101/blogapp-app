package com.blogwebsite.blogwebapp.service;

import com.blogwebsite.blogwebapp.model.Comment;
import com.blogwebsite.blogwebapp.model.Posts;
import com.blogwebsite.blogwebapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Posts> findAll() {
        return postRepository.findAll();
    }

    @Transactional
    @Override
    public void save(Posts posts) {
        postRepository.save(posts);
    }

    @Transactional
    @Override
    public Posts saveReturnObject(Posts posts) {
        return postRepository.save(posts);
    }

    @Override
    public Posts findPostById(int id) {
        return postRepository.findPostById(id);
    }

    @Override
    public Set<Posts> findPostByTagIds(Long[] tagName) {
        Set<Posts> postsList = new HashSet<>();

        for (Long tag : tagName) {
            postsList.addAll(postRepository.findPostByEachTagId(tag));
        }

        return postsList;
    }

    @Transactional
    @Override
    public void deletePostById(int id) {
        Posts post = postRepository.findPostById(id);
        postRepository.delete(post);
    }

    @Override
    public List<String> findAllUniqueAuthorsName() {
        return postRepository.findAllUniqueAuthorsName();
    }

    @Transactional
    @Override
    public void updateCompletePost(Posts posts) {
        postRepository.save(posts);
    }

    @Override
    public void updatePostWithComment(int postId, Comment comment) {
        Posts posts = postRepository.findPostById(postId);
        List<Comment> comments = posts.getComment();

        if (comments != null) {
            comments.add(comment);
            comment.setPosts(posts);
            posts.setComment(comments);
            postRepository.save(posts);
        }

    }

    @Override
    public List<Posts> findOnlyTenPost(int limit, int offset) {
        return postRepository.findOnlyTenPost(limit, offset);
    }

    @Override
    public List<Posts> findPostByAuthorName(String[] authorName) {
        return postRepository.findPostByEachAuthorName(authorName);
    }

    @Override
    public List<Posts> findAllDraftPost(String authorName) {
        return postRepository.findAllDraftPost(authorName);
    }

    @Override
    public List<Posts> findAllDraftPostOfAdmin() {
        return postRepository.findAllDraftPostOfAdmin();
    }

    @Override
    public List<Posts> findPostByTagTitleContentAuthorExcerpt(String data) {
        return postRepository.findPostByTagTitleContentAuthorExcerpt(data, data, data, data, data);
    }

    @Override
    public List<Posts> findSortedByPublishedASC(int limit, int offset) {
        return postRepository.findSortedByPublishedASC(limit, offset);
    }

    @Override
    public List<Posts> findByStartDateAndEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        return postRepository.findByStartDateAndEndDate(startDate, endDate);
    }

    @Override
    public List<String> findAuthorNameOnSearchResult(String dataTitle, String dataContent, String dataAuthor, String dataExcerpt, String dataTags) {
        return postRepository.findAuthorNameOnSearchResult(dataTitle, dataContent, dataAuthor, dataExcerpt, dataTags);
    }

}
