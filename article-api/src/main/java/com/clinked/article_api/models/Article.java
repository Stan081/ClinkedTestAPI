package com.clinked.article_api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Article title can not be blank")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Article Author can not be blank")
    private String author;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank (message = "Article content can not be blank")
    private String content;

    @Column(updatable = false,
            nullable = false
    )
    @PastOrPresent
    private LocalDate publishedDate;
}
