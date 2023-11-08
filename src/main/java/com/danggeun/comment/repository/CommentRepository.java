package com.danggeun.comment.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.danggeun.comment.dto.CommentRequestDto;
import com.danggeun.comment.dto.CommentResponseDto;

public interface CommentRepository {

	public CommentResponseDto createComment(CommentRequestDto commentRequestDto);

	public CommentResponseDto modifyComment(CommentRequestDto commentRequestDto);

	public int deleteComment(CommentRequestDto commentRequestDto);

	public List<CommentResponseDto> findByAll(Pageable pageable, int articleId);

}
