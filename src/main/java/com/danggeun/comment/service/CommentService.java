package com.danggeun.comment.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.danggeun.comment.dto.CommentRequestDto;
import com.danggeun.comment.dto.CommentResponseDto;
import com.danggeun.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;

	public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
		return commentRepository.createComment(commentRequestDto);
	}

	public CommentResponseDto modifyComment(CommentRequestDto commentRequestDto) {
		return commentRepository.modifyComment(commentRequestDto);
	}

	public void deleteComment(CommentRequestDto commentRequestDto) {
		commentRepository.deleteComment(commentRequestDto);
	}

	public List<CommentResponseDto> findAll(Pageable pageable, int articleId) {
		return commentRepository.findByAll(pageable, articleId);
	}
}
