package com.danggeun.comment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danggeun.article.service.ArticleService;
import com.danggeun.comment.dto.CommentRequestDto;
import com.danggeun.comment.dto.CommentResponseDto;
import com.danggeun.comment.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(value = "/api/comments")
@RestController
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;
	private final ArticleService articleService;

	// 댓글 등록
	@PostMapping
	public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
		return new ResponseEntity<>(commentService.createComment(commentRequestDto), HttpStatus.CREATED);
	}

	// 댓글 수정
	@PutMapping
	public ResponseEntity<CommentResponseDto> modifyComment(@RequestBody CommentRequestDto commentRequestDto) {
		// 댓글 ID 존재 여부 확인
		commentRequestDto.validateId();
		return new ResponseEntity<>(commentService.modifyComment(commentRequestDto), HttpStatus.OK);

	}

	// 댓글 삭제
	@DeleteMapping("/{commentId}")
	public ResponseEntity deleteComment(@PathVariable("commentId") Integer commentId) {
		// 댓글 삭제
		commentService.deleteComment(commentId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
