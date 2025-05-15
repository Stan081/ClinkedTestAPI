package com.clinked.article_api.controllers;

import com.clinked.article_api.models.Article;
import com.clinked.article_api.services.ArticleService;
import com.clinked.article_api.utils.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createArticle(@Valid @RequestBody Article article) {
        try {
            Article createdArticle = articleService.saveArticle(article);
            return ResponseUtil.success("Article published successfully", createdArticle);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to publish article", 500, e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<Object> listArticles(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Article> articles = articleService.getAllArticles(pageable);
            return ResponseUtil.paginatedSuccess(
                    "Articles returned successfully",
                    articles.getContent(),
                    articles.getNumber() + 1,
                    articles.getTotalPages(),
                    articles.getTotalElements()
            );
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while fetching articles", 500, e.getMessage());
        }
    }
}