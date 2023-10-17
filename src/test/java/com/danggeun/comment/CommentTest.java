package com.danggeun.comment;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.danggeun.annotation.SpringIntegrationTest;
import com.danggeun.article.dto.ArticleRequestDto;
import com.danggeun.article.enumerate.ArticleType;
import com.danggeun.article.exception.ArticleNotFoundException;
import com.danggeun.comment.controller.CommentController;
import com.danggeun.comment.dto.CommentRequestDto;
import com.danggeun.comment.dto.CommentResponseDto;
import com.danggeun.comment.service.CommentService;

@SpringIntegrationTest
public class CommentTest {
	@Autowired
	CommentController commentController;

	@Autowired
	CommentService commentService;

	CommentRequestDto commentRequestDto = null;

	ArticleRequestDto articleRequestDTO = null;

	@BeforeEach
	void setUp() {
		articleRequestDTO = new ArticleRequestDto(1, 9999, null, 9999, null, "test subject", "test context",
			ArticleType.NORMAL, null, true, null, "testDevelop", null, "testDevelop");
		commentRequestDto = new CommentRequestDto(1L, "comment context", true, null, "testDevelop", null, "testDevelop",
			articleRequestDTO);
	}

	// 댓글 등록
	@Test
	@DisplayName("댓글 등록 시 게시글 정보가 있어야 한다.")
	public void commentCreateNonArticle() {
		// 게시글 Dto 정보가 있느냐. 없다면 ArticleNotFound 예외 발생
		commentRequestDto.setArticleDto(null);
		assertThrows(ArticleNotFoundException.class, () -> {
			commentController.createComment(commentRequestDto);
		});
	}

	@Test
	@DisplayName("댓글 등록 시 게시글 ID 가 없다면 예외 발생")
	void commentCreateArticleIdExists() {
		// 게시글 Dto 정보는 있지만, 게시글 ID 가 없는 경우 예외 발생
		articleRequestDTO.setArticleId(null);
		commentRequestDto.setArticleDto(articleRequestDTO);
		assertThrows(IllegalArgumentException.class, () -> {
			commentController.createComment(commentRequestDto);
		});
	}

	@Test
	@DisplayName("정상 댓글 등록")
	void commentCreate() {
		ResponseEntity<CommentResponseDto> result = commentController.createComment(commentRequestDto);

		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	@DisplayName("댓글 수정, 삭제 시 게시글 정보 확인")
	void commentModifyAndDeleteCheckArticle() {
		commentRequestDto.setArticleDto(null);
		// 게시글 정보 없음
		assertThrows(ArticleNotFoundException.class, () -> {
			commentController.modifyComment(commentRequestDto);
		});
		assertThrows(ArticleNotFoundException.class, () -> {
			commentController.deleteComment(commentRequestDto);
		});

		// 게시글 정보는 있지만 게시글 ID 가 없는 경우
		articleRequestDTO.setArticleId(null);
		commentRequestDto.setArticleDto(articleRequestDTO);
		assertThrows(IllegalArgumentException.class, () -> {
			commentController.modifyComment(commentRequestDto);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			commentController.deleteComment(commentRequestDto);
		});

		// 게시글 정보가 있지만 존재하지 않는 게시글 정보 인 경우
		articleRequestDTO.setArticleId(999999);
		commentRequestDto.setArticleDto(articleRequestDTO);
		assertThrows(ArticleNotFoundException.class, () -> {
			commentController.modifyComment(commentRequestDto);
		});
		assertThrows(ArticleNotFoundException.class, () -> {
			commentController.deleteComment(commentRequestDto);
		});
	}

	// 댓글 수정
	@Test
	@DisplayName("정상 댓글 수정")
	void commentModifiy() {
		ResponseEntity<CommentResponseDto> result = commentController.modifyComment(commentRequestDto);

		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	// 댓글 삭제
	@Test
	@DisplayName("정상 댓글 삭제")
	void commentDelete() {
		ResponseEntity<CommentResponseDto> result = commentController.deleteComment(commentRequestDto);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	// 댓글 조회
	@Test
	@DisplayName("댓글 조회")
	void commentFindAll() {
		ResponseEntity<List<CommentResponseDto>> result = commentController.findByAll(1, PageRequest.of(0, 3));

		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().size()).isEqualTo(3);

	}

}
