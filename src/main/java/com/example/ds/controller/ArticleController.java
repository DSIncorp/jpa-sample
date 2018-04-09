package com.example.ds.controller;

import com.example.ds.exception.ResourceNotFoundException;
import com.example.ds.model.Article;
import com.example.ds.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles")
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @GetMapping("/articles/rating/{rating}")
    public List<Article> getArticlesByRating(@Valid @PathVariable int rating) {
        return articleRepository.findByRating(rating);
    }

    @GetMapping("/articles/{id}")
    public Article getArticleById(@PathVariable(value = "id") Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
    }

    @PutMapping("/articles/{id}")
    public Article updateArticle(@PathVariable(value = "id") Long articleId,
                              @Valid @RequestBody Article articleDetails) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        return articleRepository.save(article);
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable(value = "id") Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));

        articleRepository.delete(article);
        return ResponseEntity.ok().build();
    }
}
