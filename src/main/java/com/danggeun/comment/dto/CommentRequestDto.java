package com.danggeun.comment.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.util.ObjectUtils;

import com.danggeun.article.dto.ArticleRequestDto;
import com.danggeun.article.exception.ArticleNotFoundException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 댓글 request 전용 Data Transfer Object
 * DTO 검증 메소드 존재
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {

	private Long commentId;
	private String context;
	private boolean active;
	private LocalDateTime registeredDate;
	private String registeredId;
	private LocalDateTime modifiedDate;
	private String modifiedId;

	private ArticleRequestDto articleDto;

	/**
	 * 댓글 ID 값 존재 여부 확인
	 */
	public void hasId() {
		if (Objects.isNull(this.getCommentId())) {
			throw new IllegalArgumentException("댓글 ID가 없습니다.");
		}

		articleExists();
	}

	/**
	 * 게시글 정보 확인 및 게시글 ID 확인
	 */
	public void articleExists() {
		if (ObjectUtils.isEmpty(this.articleDto)) {
			throw new ArticleNotFoundException("존재 하지 않는 게시물 입니다.");
		}
		// 게시글 ID 값 존재 여부 확인
		articleDto.hasId();
	}
}
