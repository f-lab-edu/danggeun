package com.danggeun.article.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danggeun.article.domain.Article;

public interface ArticleJpaRepository extends JpaRepository<Article, Integer> {
}
