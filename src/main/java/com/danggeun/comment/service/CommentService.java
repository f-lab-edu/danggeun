package com.danggeun.comment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danggeun.article.domain.Article;
import com.danggeun.article.exception.ArticleNotFoundException;
import com.danggeun.article.repository.ArticleRepository;
import com.danggeun.article.repository.jpa.ArticleJpaRepository;
import com.danggeun.comment.domain.Comment;
import com.danggeun.comment.dto.CommentEntityMapperImpl;
import com.danggeun.comment.dto.CommentRequestDto;
import com.danggeun.comment.dto.CommentResponseDto;
import com.danggeun.comment.repository.CommentRepository;
import com.danggeun.comment.repository.jpa.CommentJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
	private final CommentRepository commentRepository;
	private final CommentJpaRepository commentJpaRepository;

	private final ArticleRepository articleRepository;
	private final ArticleJpaRepository articleJpaRepository;
	private final CommentEntityMapperImpl commentEntityMapper = new CommentEntityMapperImpl();

	public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
		// 게시글 정보 확인
		Integer articleId = commentRequestDto.getArticleId();
		Optional<Article> find = articleJpaRepository.findById(articleId);
		Article findArticle = find.orElseThrow(ArticleNotFoundException::new);

		Comment commentEntity = commentEntityMapper.toCommentEntity(commentRequestDto);
		commentEntity.setArticle(findArticle);
		commentJpaRepository.save(commentEntity);

		// Entity -> Response DTO
		Optional<Comment> findComment = commentJpaRepository.findById(commentEntity.getCommentId());
		return new CommentResponseDto(findComment.get());
	}

	public CommentResponseDto modifyComment(CommentRequestDto commentRequestDto) {
		Optional<Comment> find = commentJpaRepository.findById(commentRequestDto.getCommentId());
		Comment comment = find.get();

		comment.setContext(commentRequestDto.getContext());
		Optional<Comment> result = commentJpaRepository.findById(commentRequestDto.getCommentId());
		return new CommentResponseDto(result.get());
	}

	public void deleteComment(CommentRequestDto commentRequestDto) {

		commentRepository.deleteComment(commentRequestDto);
	}

	public List<CommentResponseDto> findAll(Pageable pageable, int articleId) {
		return commentRepository.findByAll(pageable, articleId);
	}
}
