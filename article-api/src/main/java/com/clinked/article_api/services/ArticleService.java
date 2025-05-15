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
        return (Page<Article>) articleRepository.findAll(pageable);
    }

    public Map<LocalDate, Long> getArticleCountsByDate() {
        // Fetch all articles from the repository
        List<Article> articles = articleRepository.findAll();

        // Define the date range: starting from 6 days ago to today
        LocalDate startDate = LocalDate.now().minusDays(6); // Example: last 7 days
        LocalDate endDate = LocalDate.now();

        // Create a map where each date in the range is a key, and the value is the count of articles published on that date
        return startDate.datesUntil(endDate.plusDays(1)) // Include the end date
            .collect(Collectors.toMap(
                date -> date, // Use the date as the key
                date -> articles.stream() // Count articles published on the specific date
                        .filter(article -> article.getPublishedDate().equals(date))
                        .count()
                )
            );
    }
}