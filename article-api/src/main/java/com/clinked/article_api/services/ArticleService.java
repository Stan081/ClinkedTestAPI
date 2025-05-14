package com.clinked.article_api.services;

import com.clinked.article_api.models.Article;
import com.clinked.article_api.repositories.ArticleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public Article saveArticle(Article article) {
        return repository.save(article);
    }

    public List<Article> getAllArticles() {
        return repository.findAll();
    }
}