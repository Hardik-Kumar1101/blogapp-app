package com.blogwebsite.blogwebapp.service;

import com.blogwebsite.blogwebapp.model.Comment;
import com.blogwebsite.blogwebapp.model.Posts;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface PostService {

    List<Posts> findAll();

    void save(Posts posts);

    Posts saveReturnObject(Posts posts);

    Posts findPostById(int id);

    Set<Posts> findPostByTagIds(Long[] tagName);

    void deletePostById(int id);

    void updateCompletePost(Posts posts);

    List<String> findAllUniqueAuthorsName();

    List<Posts> findPostByAuthorName(String[] authorName);

    void updatePostWithComment(int postId, Comment comment);

    List<Posts> findOnlyTenPost(int limit, int offset);

    List<Posts> findAllDraftPost(String authorName);

    List<Posts> findAllDraftPostOfAdmin();

    List<Posts> findPostByTagTitleContentAuthorExcerpt(String data);

    List<Posts> findSortedByPublishedASC(int limit, int offset);

    List<Posts> findByStartDateAndEndDate(LocalDateTime startDate, LocalDateTime endDate);

    List<String> findAuthorNameOnSearchResult(String dataTitle, String dataContent, String dataAuthor, String dataExcerpt, String dataTags);

}
