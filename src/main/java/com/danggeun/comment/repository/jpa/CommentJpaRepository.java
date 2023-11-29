package com.danggeun.comment.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.danggeun.comment.domain.Comment;
import com.danggeun.comment.dto.CommentResponseDto;

public interface CommentJpaRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from Comment c where c.article.articleId = :articleId")
	Page<CommentResponseDto> findByArticleId(Pageable pageable, @Param("articleId") Integer articleId);
}
