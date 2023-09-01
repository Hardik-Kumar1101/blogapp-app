package com.blogwebsite.blogwebapp.repository;

import com.blogwebsite.blogwebapp.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tags, Integer> {

    Tags findById(int id);

    Tags findByName(String name);

    Tags save(Tags tags);

    List<Tags> findAll();

    boolean existsByName(String name);

    @Query("SELECT DISTINCT t FROM Posts p JOIN p.tags t WHERE p.is_published = TRUE AND ((p.title IN :dataTitle) OR (p.content LIKE %:dataContent%) OR (p.author IN :dataAuthor) OR (p.excerpt LIKE %:dataExcerpt%))")
    Set<Tags> findTagNameOnSearchResult(@Param("dataTitle") String dataTitle, @Param("dataContent") String dataContent, @Param("dataAuthor") String dataAuthor, @Param("dataExcerpt") String dataExcerpt);

}
