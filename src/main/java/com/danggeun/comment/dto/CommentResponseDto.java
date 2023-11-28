package com.danggeun.comment.dto;

import java.time.LocalDateTime;

import com.danggeun.comment.domain.Comment;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 댓글 response 전용 Data Transfer Object
 */
@Data
@NoArgsConstructor
public class CommentResponseDto {

	public CommentResponseDto(Comment comment) {
		this.commentId = comment.getCommentId();
		this.context = comment.getContext();
		this.active = comment.isActive();
		this.registeredDate = comment.getRegisteredDate();
		this.modifiedDate = comment.getModifiedDate();
		this.articleId = comment.getArticle().getArticleId();
	}

	private Integer commentId;
	private String context;
	private boolean active;
	private LocalDateTime registeredDate;
	private String registeredId;
	private LocalDateTime modifiedDate;
	private String modifiedId;

	private Integer articleId;

}
