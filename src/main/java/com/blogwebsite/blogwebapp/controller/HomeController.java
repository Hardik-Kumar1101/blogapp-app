package com.blogwebsite.blogwebapp.controller;

import com.blogwebsite.blogwebapp.model.Posts;
import com.blogwebsite.blogwebapp.model.Tags;
import com.blogwebsite.blogwebapp.service.PostService;
import com.blogwebsite.blogwebapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class HomeController {
    final int PAGE_LIMIT = 10;
    final int PAGE_STARTING = 0;
    private final PostService postService;
    private final TagService tagService;

    @Autowired
    public HomeController(PostService postService, TagService tagService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Tags> tagsList = tagService.findAll();
        List<Posts> postData = postService.findOnlyTenPost(PAGE_LIMIT, PAGE_STARTING);
        List<String> theAllUniqueAuthorsName = postService.findAllUniqueAuthorsName();
        model.addAttribute("page", PAGE_STARTING);
        model.addAttribute("postData", postData);
        model.addAttribute("tagsList", tagsList);
        model.addAttribute("allUniqueAuthorsName", theAllUniqueAuthorsName);

        return "home";
    }

    @GetMapping("/next/{page}")
    public String nextPage(@PathVariable(name = "page", required = false) int page, Model model) {
        List<Tags> tagsList = tagService.findAll();
        List<Posts> postData = postService.findOnlyTenPost(PAGE_LIMIT, page * PAGE_LIMIT);
        List<String> theAllUniqueAuthorsName = postService.findAllUniqueAuthorsName();
        model.addAttribute("page", page);
        model.addAttribute("postData", postData);
        model.addAttribute("tagsList", tagsList);
        model.addAttribute("allUniqueAuthorsName", theAllUniqueAuthorsName);

        return "home";
    }

    @GetMapping("/previous/{page}")
    public String previousPage(@PathVariable(name = "page", required = false) int page, Model model) {
        page = page < 0 ? 0 : page;

        List<Tags> tagsList = tagService.findAll();
        List<Posts> postData = postService.findOnlyTenPost(PAGE_LIMIT, page * PAGE_LIMIT);
        List<String> theAllUniqueAuthorsName = postService.findAllUniqueAuthorsName();
        model.addAttribute("page", page);
        model.addAttribute("postData", postData);
        model.addAttribute("tagsList", tagsList);
        model.addAttribute("allUniqueAuthorsName", theAllUniqueAuthorsName);

        return "home";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute("search") String search, Model model) {
        List<Posts> searchResult = new ArrayList<>();

        if (search.isEmpty()) {
            searchResult.addAll(postService.findOnlyTenPost(PAGE_LIMIT, PAGE_STARTING));
        } else {
            List<Posts> postsSearchResult = postService.findPostByTagTitleContentAuthorExcerpt(search);
            searchResult.addAll(postsSearchResult);
        }

        Set<Tags> tagsList = tagService.findTagNameOnSearchResult(search, search, search, search);
        List<String> theAllUniqueAuthorsName = postService.findAuthorNameOnSearchResult(search, search, search, search, search);
        model.addAttribute("page", PAGE_STARTING);
        model.addAttribute("tagsList", tagsList);
        model.addAttribute("postData", searchResult);
        model.addAttribute("allUniqueAuthorsName", theAllUniqueAuthorsName);

        return "home";
    }

    @PostMapping("/filter/tags")
    public String filterForTags(@RequestParam(name = "selectedTags", required = false) Long[] selectedTags, Model theModel) {
        Set<Posts> searchResult = new HashSet<>();

        if (selectedTags != null) {
            searchResult = postService.findPostByTagIds(selectedTags);
        }

        List<Tags> tagsList = tagService.findAll();
        List<String> theAllUniqueAuthorsName = postService.findAllUniqueAuthorsName();
        theModel.addAttribute("page", PAGE_STARTING);
        theModel.addAttribute("tagsList", tagsList);
        theModel.addAttribute("postData", searchResult);
        theModel.addAttribute("allUniqueAuthorsName", theAllUniqueAuthorsName);

        return "home";
    }

    @PostMapping("/filter/authors")
    public String filterForAuthors(@RequestParam(name = "selectedAuthorName", required = false) String[] selectedAuthorName, Model theModel) {
        List<Posts> searchResult = new ArrayList<>();

        if (selectedAuthorName != null) {
            searchResult = postService.findPostByAuthorName(selectedAuthorName);
        }

        List<Tags> tagsList = tagService.findAll();
        List<String> theAllUniqueAuthorsName = postService.findAllUniqueAuthorsName();
        theModel.addAttribute("page", PAGE_STARTING);
        theModel.addAttribute("tagsList", tagsList);
        theModel.addAttribute("postData", searchResult);
        theModel.addAttribute("allUniqueAuthorsName", theAllUniqueAuthorsName);

        return "home";
    }

    @PostMapping("/filter/date")
    public String filterForDate(@RequestParam(name = "selectedDateStart", required = false) String selectedDateStart, @RequestParam(name = "selectedDateEnd", required = false) String selectedDateEnd, Model theModel) {
        List<Tags> tagsList = tagService.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(selectedDateStart, formatter);
        LocalDate endDate = LocalDate.parse(selectedDateEnd, formatter);
        LocalDateTime startDateTime = startDate.atTime(LocalTime.MIDNIGHT);
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MIDNIGHT);
        List<Posts> searchResult = postService.findByStartDateAndEndDate(startDateTime, endDateTime);
        List<String> theAllUniqueAuthorsName = postService.findAllUniqueAuthorsName();
        theModel.addAttribute("page", PAGE_STARTING);
        theModel.addAttribute("tagsList", tagsList);
        theModel.addAttribute("postData", searchResult);
        theModel.addAttribute("allUniqueAuthorsName", theAllUniqueAuthorsName);

        return "home";
    }

    @GetMapping("/draft/{authorName}/{userRole}")
    public String draft(@PathVariable(name = "authorName") String author, @PathVariable(name = "userRole", required = false) String userRole, Model model) {
        List<Posts> draftPosts = new ArrayList<>();

        if (userRole.equals("ROLE_ADMIN")) {
            draftPosts = postService.findAllDraftPostOfAdmin();
        } else {
            draftPosts = postService.findAllDraftPost(author);
        }

        model.addAttribute("postData", draftPosts);

        return "draft";

    }

}
