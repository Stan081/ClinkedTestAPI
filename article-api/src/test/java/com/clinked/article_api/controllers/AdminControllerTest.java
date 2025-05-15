package com.clinked.article_api.controllers;

import com.clinked.article_api.services.ArticleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {
    private final ArticleService articleService = mock(ArticleService.class);
    private final AdminController adminController = new AdminController(articleService);

    @Test
    void testCountArticlesByDate_Success() {
        Map<LocalDate, Long> counts = Map.of(LocalDate.now(), 5L);
        when(articleService.getArticleCountsByDate()).thenReturn(counts);

        ResponseEntity<Object> response = adminController.countArticlesByDate();

        assertEquals(200, response.getStatusCodeValue());
        verify(articleService, times(1)).getArticleCountsByDate();
    }
}