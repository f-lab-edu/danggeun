package com.danggeun.comment;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.danggeun.annotation.SpringIntegrationTest;
import com.danggeun.comment.domain.Comment;
import com.danggeun.comment.dto.CommentEntityMapperImpl;
import com.danggeun.comment.dto.CommentRequestDto;
import com.danggeun.comment.repository.jpa.CommentJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringIntegrationTest
public class CommentTest {
	@Autowired
	CommentJpaRepository commentJpaRepository;
	@PersistenceContext
	EntityManager entityManager;
	CommentRequestDto commentRequestDto;
	CommentEntityMapperImpl commentEntityMapper = new CommentEntityMapperImpl();
	Comment comment;

	@BeforeEach
	void setUp() {
		commentRequestDto = new CommentRequestDto(null,
			"comment context",
			true,
			null,
			"testDevelop",
			null,
			"testDevelop",
			1
		);
		comment = commentEntityMapper.toCommentEntity(commentRequestDto);
		commentJpaRepository.save(comment);
	}

	@Test
	@DisplayName("정상 댓글 등록")
	void commentCreate() {
		Comment createdComment = commentEntityMapper.toCommentEntity(commentRequestDto);
		commentJpaRepository.save(createdComment);
		assertThat(createdComment.getCommentId()).isNotNull();
	}

	// 댓글 수정
	@Test
	@DisplayName("정상 댓글 수정")
	void commentModify() {
		Comment modifyComment = null;
		Optional<Comment> find = commentJpaRepository.findById(comment.getCommentId());

		if (find.isPresent()) {
			modifyComment = find.get();

			modifyComment.setContext("변경변경");

			entityManager.flush();
			entityManager.clear();

			Optional<Comment> result = commentJpaRepository.findById(comment.getCommentId());
			assertThat(result.orElseGet(() -> null)).isNotNull();
			assertThat(result.orElseGet(() -> null).getContext()).isEqualTo("변경변경");

		} else {
			fail("댓글 조회 되지 않음");
		}
	}

	// 댓글 삭제
	@Test
	@DisplayName("정상 댓글 삭제")
	void commentDelete() {
		commentJpaRepository.delete(comment);
		entityManager.flush();
		entityManager.clear();

		Optional<Comment> find = commentJpaRepository.findById(comment.getCommentId());
		assertThat(find.isEmpty()).isTrue();
	}

}
