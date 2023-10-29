package com.danggeun.comment.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.danggeun.article.repository.ArticleRepository;
import com.danggeun.comment.dto.CommentRequestDto;
import com.danggeun.comment.dto.CommentResponseDto;
import com.danggeun.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final ArticleRepository articleRepository;

	public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
		return commentRepository.createComment(commentRequestDto);
	}

	public CommentResponseDto modifyComment(CommentRequestDto commentRequestDto) {
		int articleId = commentRequestDto.getArticleDto().getArticleId();
		existsArticleCheck(articleId);

		return commentRepository.modifyComment(commentRequestDto);
	}

	public void deleteComment(CommentRequestDto commentRequestDto) {
		int articleId = commentRequestDto.getArticleDto().getArticleId();
		existsArticleCheck(articleId);
		
		commentRepository.deleteComment(commentRequestDto);
	}

	public List<CommentResponseDto> findAll(Pageable pageable, int articleId) {
		return commentRepository.findByAll(pageable, articleId);
	}

	private void existsArticleCheck(int articleId) {
		articleRepository.findById(articleId);
	}
}
