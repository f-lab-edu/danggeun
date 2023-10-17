package com.danggeun.comment.dto;

import java.time.LocalDateTime;

import com.danggeun.article.dto.ArticleResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 댓글 response 전용 Data Transfer Object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

	private Long commentId;
	private String context;
	private boolean active;
	private LocalDateTime registeredDate;
	private String registeredId;
	private LocalDateTime modifiedDate;
	private String modifiedId;

	private ArticleResponseDto articleDto;

}
