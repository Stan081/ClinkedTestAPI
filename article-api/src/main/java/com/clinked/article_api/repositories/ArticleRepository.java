package com.clinked.article_api.repositories;

import com.clinked.article_api.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // Custom query methods can be defined here if needed
}
