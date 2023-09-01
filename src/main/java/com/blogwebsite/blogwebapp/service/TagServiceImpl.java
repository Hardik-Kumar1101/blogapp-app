package com.blogwebsite.blogwebapp.service;

import com.blogwebsite.blogwebapp.model.Tags;
import com.blogwebsite.blogwebapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void save(Tags tags) {
        tagRepository.save(tags);
    }

    @Override
    public Tags findById(int id) {
        return tagRepository.findById(id);
    }

    @Override
    public Tags findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public boolean isExistsByName(String name) {
        return tagRepository.existsByName(name);
    }

    @Override
    public List<Tags> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Set<Tags> findTagNameOnSearchResult(String dataTitle, String dataContent, String dataAuthor, String dataExcerpt) {
        return tagRepository.findTagNameOnSearchResult(dataTitle, dataContent, dataAuthor, dataExcerpt);
    }
}
