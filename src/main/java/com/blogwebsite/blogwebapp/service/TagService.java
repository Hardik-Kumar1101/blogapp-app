package com.blogwebsite.blogwebapp.service;

import com.blogwebsite.blogwebapp.model.Tags;

import java.util.List;
import java.util.Set;

public interface TagService {

    void save(Tags tags);

    Tags findById(int id);

    Tags findByName(String name);

    boolean isExistsByName(String name);

    List<Tags> findAll();

    Set<Tags> findTagNameOnSearchResult(String dataTitle, String dataContent, String dataAuthor, String dataExcerpt);
}
