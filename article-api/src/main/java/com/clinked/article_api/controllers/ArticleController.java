package com.clinked.article_api.controllers;

import com.clinked.article_api.dtos.ArticleDto;
import com.clinked.article_api.models.Article;
import com.clinked.article_api.services.ArticleService;
import com.clinked.article_api.utils.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createArticle(@Valid @RequestBody ArticleDto articleDto) {
        try {
            if (articleDto.getTitle() == null || articleDto.getTitle().isBlank() ||
                    articleDto.getContent() == null || articleDto.getContent().isBlank() ||
                    articleDto.getAuthor() == null || articleDto.getAuthor().isBlank()) {
                throw new IllegalArgumentException("Author, Title, and Content are required");
            }
            if (articleDto.getTitle().length() > 100) {
                throw new IllegalArgumentException("Title must not exceed 100 characters");
            }
            Article article = new Article();
            article.setTitle(articleDto.getTitle());
            article.setContent(articleDto.getContent());
            article.setAuthor(articleDto.getAuthor());
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