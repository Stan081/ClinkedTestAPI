package com.clinked.article_api.controllers;

import com.clinked.article_api.models.Article;
import com.clinked.article_api.services.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @PostMapping("/")
    public Article createArticle(@Valid @RequestBody Article article) {
        return service.saveArticle(article);
    }

        @GetMapping("/")
    public ResponseEntity<Map<String, Object>> listArticles() {
        try {
            List<Article> articles = service.getAllArticles();
            return ResponseEntity.ok(Map.of(
                    "status", "articles returned successfully",
                    "status-code", 200,
                    "data", articles
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "code", 500,
                    "message", "An error occurred while fetching articles",
                    "error", e.getMessage()
            ));
        }
    }
}