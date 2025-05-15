package com.clinked.article_api.services;

import com.clinked.article_api.models.Article;
import com.clinked.article_api.repositories.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ArticleServiceTest {
    private final ArticleRepository articleRepository = mock(ArticleRepository.class);
    private final ArticleService articleService = new ArticleService(articleRepository);

    @Test
    void testSaveArticle() {
        Article article = new Article();
        when(articleRepository.save(article)).thenReturn(article);

        Article result = articleService.saveArticle(article);

        assertEquals(article, result);
        verify(articleRepository, times(1)).save(article);
    }

    @Test
    void testGetAllArticles() {
        Page<Article> page = new PageImpl<>(List.of(new Article()));
        when(articleRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Article> result = articleService.getAllArticles(PageRequest.of(0, 10));

        assertEquals(page, result);
        verify(articleRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testGetArticleCountsByDate() {
        List<Article> articles = List.of(new Article());
        when(articleRepository.findAll()).thenReturn(articles);

        Map<LocalDate, Long> result = articleService.getArticleCountsByDate();

        assertEquals(7, result.size());
        verify(articleRepository, times(1)).findAll();
    }
}