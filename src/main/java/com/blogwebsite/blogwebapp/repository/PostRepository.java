package com.blogwebsite.blogwebapp.repository;

import com.blogwebsite.blogwebapp.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer> {

    Posts save(Posts posts);

    List<Posts> findAll();

    Posts findPostById(int id);

    @Query("SELECT p FROM Posts p WHERE p.is_published = TRUE ORDER BY p.published_at ASC LIMIT :limit OFFSET :offset")
    List<Posts> findSortedByPublishedASC(@Param("limit") int limit, @Param("offset") int offset);

    @Query("SELECT DISTINCT p FROM Posts p JOIN p.tags t WHERE (t.id = :tagId) AND p.is_published = TRUE")
    Set<Posts> findPostByEachTagId(@Param("tagId") Long tagId);

    @Query("SELECT p FROM Posts p WHERE (p.author IN :authorName) AND p.is_published = TRUE")
    List<Posts> findPostByEachAuthorName(@Param("authorName") String[] authorName);

    @Query("SELECT DISTINCT p.author FROM Posts p WHERE p.is_published = TRUE")
    List<String> findAllUniqueAuthorsName();

    @Query(value = "SELECT * FROM posts p WHERE p.is_published = TRUE ORDER BY p.published_at DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Posts> findOnlyTenPost(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT * FROM posts p WHERE p.is_published = FALSE AND p.author = :authorName", nativeQuery = true)
    List<Posts> findAllDraftPost(@Param("authorName") String name);

    @Query("SELECT p FROM Posts p WHERE p.is_published = FALSE AND p.author LIKE '  %'")
    List<Posts> findAllDraftPostOfAdmin();

    @Query("SELECT p FROM Posts p WHERE p.published_at BETWEEN :startDate AND :endDate")
    List<Posts> findByStartDateAndEndDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT DISTINCT p FROM Posts p JOIN p.tags t WHERE ((p.title IN :dataTitle) OR (p.content LIKE %:dataContent%) OR (p.author IN :dataAuthor) OR (p.excerpt LIKE %:dataExcerpt%) OR (t.name IN :dataTags)) AND p.is_published = TRUE")
    List<Posts> findPostByTagTitleContentAuthorExcerpt(@Param("dataTitle") String dataTitle, @Param("dataContent") String dataContent, @Param("dataAuthor") String dataAuthor, @Param("dataExcerpt") String dataExcerpt, @Param("dataTags") String dataTags);

    @Query("SELECT DISTINCT p.author FROM Posts p JOIN p.tags t WHERE ((p.title IN :dataTitle) OR (p.content LIKE %:dataContent%) OR (p.author IN :dataAuthor) OR (p.excerpt LIKE %:dataExcerpt%) OR (t.name IN :dataTags)) AND p.is_published = TRUE")
    List<String> findAuthorNameOnSearchResult(@Param("dataTitle") String dataTitle, @Param("dataContent") String dataContent, @Param("dataAuthor") String dataAuthor, @Param("dataExcerpt") String dataExcerpt, @Param("dataTags") String dataTags);

}
