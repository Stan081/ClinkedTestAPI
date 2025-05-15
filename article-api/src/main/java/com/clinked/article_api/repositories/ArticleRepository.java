package com.clinked.article_api.repositories;

            import com.clinked.article_api.models.Article;
            import org.springframework.data.jpa.repository.JpaRepository;
            import org.springframework.stereotype.Repository;
            import org.springframework.data.jpa.repository.Query;
            import org.springframework.data.repository.query.Param;
            import java.time.LocalDate;
            import java.util.List;

            @Repository
            public interface ArticleRepository extends JpaRepository<Article, Long> {

                List<Article> findByPublishedDateBetween(LocalDate startDate, LocalDate endDate);

                @Query("SELECT a.publishedDate AS date, COUNT(a) AS count " +
                        "FROM Article a " +
                        "WHERE a.publishedDate BETWEEN :startDate AND :endDate " +
                        "GROUP BY a.publishedDate " +
                        "ORDER BY a.publishedDate ASC")
                List<Object[]> countArticlesByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
            }