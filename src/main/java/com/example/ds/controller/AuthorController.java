package com.example.ds.controller;

import com.example.ds.exception.ResourceNotFoundException;
import com.example.ds.model.Article;
import com.example.ds.model.Author;
import com.example.ds.repository.ArticleRepository;
import com.example.ds.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/authors")
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @PostMapping("/authors")
    public Author createAuthor(@Valid @RequestBody Author author) {
        return authorRepository.save(author);
    }

    @PostMapping("/authors/{authorId}/articles")
    public Article addArticle(@Valid @PathVariable long authorId, @Valid @RequestBody Article article) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
        article.setAuthor(author);
        return articleRepository.save(article);
    }

    @GetMapping("/authors/{authorId}/articles")
    public List<Article> getArticles(@Valid @PathVariable long authorId) {
        return articleRepository.findByAuthor(authorId);
    }

    @GetMapping("/authors/{id}")
    public Author getAuthorById(@PathVariable(value = "id") Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
    }

    @PutMapping("/authors/{id}")
    public Author updateAuthor(@PathVariable(value = "id") Long authorId,
                              @Valid @RequestBody Author authorDetails) {

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
        author.setName(authorDetails.getName());
        return authorRepository.save(author);
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable(value = "id") Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));

        authorRepository.delete(author);
        return ResponseEntity.ok().build();
    }
}
