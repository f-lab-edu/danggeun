package com.danggeun.article.dto;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.danggeun.article.enumerate.ArticleType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 게시글 request 전용 Data Transfer Object
 * DTO 검증 메소드 존재
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequestDto {

	private Integer articleId;
	private Integer userId;
	private Integer commentId;
	private Integer regionId;
	private Integer groupId;
	private String subject;
	private String context;
	private ArticleType articleType;
	private Integer price;
	private boolean active;
	private Date registeredDate;
	private String registeredId;
	private Date modifiedDate;
	private String modifiedId;

	/**
	 * 게시글 ID 값 존재 여부 확인
	 */
	public void validId() {
		if (this.getArticleId() == null) {
			throw new IllegalArgumentException("게시글 ID가 없습니다.");
		}
	}

	/**
	 * 게시글 필수 값 validation
	 */
	public void validateArticleNullable() {
		if (this.getUserId() == null
			|| this.getRegionId() == null
			|| !StringUtils.hasText(this.getSubject())
			|| !StringUtils.hasText(this.getContext())
		) {
			throw new IllegalArgumentException("게시글 필수값 NULL 입니다.");
		}

		switch (this.getArticleType()) {
			case NORMAL:
				break;
			case GROUP:
				if (this.getGroupId() == null || this.getArticleId() == null) {
					throw new IllegalArgumentException("게시글 필수값 NULL 입니다.");
				}
				break;
			case TRADE:
				if (this.getPrice() == null || this.getArticleId() == null) {
					throw new IllegalArgumentException("게시글 필수값 NULL 입니다.");
				}
				break;
			default:
				throw new IllegalArgumentException("정상적인 게시글 타입이 아닙니다.");
		}

	}

}
