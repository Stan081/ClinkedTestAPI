package com.clinked.article_api.services;

import com.clinked.article_api.models.Article;
import com.clinked.article_api.repositories.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public Page<Article> getAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

   public Map<LocalDate, Long> getArticleCountsByDate() {
       List<Article> articles = articleRepository.findAll();
       LocalDate startDate = LocalDate.now().minusDays(6); // Example: last 30 days
       LocalDate endDate = LocalDate.now();

       return startDate.datesUntil(endDate.plusDays(1))
               .collect(Collectors.toMap(
                       date -> date,
                       date -> articles.stream()
                               .filter(article -> article.getPublishedDate().equals(date))
                               .count()
               ));
   }
}