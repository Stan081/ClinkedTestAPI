package com.clinked.article_api.controllers;

import com.clinked.article_api.services.ArticleService;
import com.clinked.article_api.utils.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ArticleService articleService;

    public AdminController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/weekly-article-statistics")
    public ResponseEntity<Object> countArticlesByDate() {
        try {
            Map<LocalDate, Long> counts = articleService.getArticleCountsByDate();
            return ResponseUtil.success("Weekly statistics returned succesfully",counts);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while counting articles by date", 500, e.getMessage());
        }
    }
}
