package com.clinked.article_api.controllers;

        import com.clinked.article_api.dtos.ArticleDto;
        import com.clinked.article_api.models.Article;
        import com.clinked.article_api.services.ArticleService;
        import org.junit.jupiter.api.Test;
        import org.springframework.http.ResponseEntity;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.PageImpl;
        import org.springframework.data.domain.PageRequest;

        import java.util.List;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.mockito.Mockito.*;

        class ArticleControllerTest {
            private final ArticleService articleService = mock(ArticleService.class);
            private final ArticleController articleController = new ArticleController(articleService);

            @Test
            void testCreateArticle_Success() {
                ArticleDto articleDto = new ArticleDto();
                articleDto.setTitle("Sample Title");
                articleDto.setContent("Sample Content");
                articleDto.setAuthor("Sample Author");

                Article article = new Article();
                article.setTitle("Sample Title");
                article.setContent("Sample Content");
                article.setAuthor("Sample Author");

                when(articleService.saveArticle(article)).thenReturn(article);

                ResponseEntity<Object> response = articleController.createArticle(articleDto);

                assertEquals(200, response.getStatusCodeValue());
                verify(articleService, times(1)).saveArticle(article);
            }

            @Test
            void testListArticles_Success() {
                Page<Article> page = new PageImpl<>(List.of(new Article()));
                when(articleService.getAllArticles(any(PageRequest.class))).thenReturn(page);

                ResponseEntity<Object> response = articleController.listArticles(1, 10);

                assertEquals(200, response.getStatusCodeValue());
                verify(articleService, times(1)).getAllArticles(any(PageRequest.class));
            }
        }