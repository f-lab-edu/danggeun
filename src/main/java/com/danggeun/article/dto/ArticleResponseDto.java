package com.danggeun.article.dto;

import java.time.LocalDateTime;

import com.danggeun.article.domain.Article;
import com.danggeun.article.enumerate.ArticleType;

import lombok.Data;

/**
 * 게시글 response 전용 Data Transfer Object
 */
@Data
public class ArticleResponseDto {

	public ArticleResponseDto() {
	}

	public ArticleResponseDto(Article article) {
		this.articleId = article.getArticleId();
		this.userId = article.getUserId();
		this.regionId = article.getRegionId();
		this.groupId = article.getGroupId();
		this.subject = article.getSubject();
		this.context = article.getContext();
		this.articleType = article.getArticleType();
		this.price = article.getPrice();
		this.active = article.isActive();
		this.registeredDate = article.getRegisteredDate();
		this.modifiedDate = article.getModifiedDate();
	}

	private Integer articleId;
	private Integer userId;
	private Integer regionId;
	private Integer groupId;
	private String subject;
	private String context;
	private ArticleType articleType;
	private Integer price;
	private boolean active;
	private LocalDateTime registeredDate;
	private String registeredId;
	private LocalDateTime modifiedDate;
	private String modifiedId;

}
