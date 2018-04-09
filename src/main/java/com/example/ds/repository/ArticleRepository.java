package com.example.ds.repository;

import com.example.ds.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("FROM Article a WHERE AUTHOR_ID = :authorId")
    List<Article> findByAuthor(@Param("authorId") long authorId);

    @Query("FROM Article a WHERE rating = :rating")
    List<Article> findByRating(@Param("rating") int rating);
}
