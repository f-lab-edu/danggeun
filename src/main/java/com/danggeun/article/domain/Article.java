package com.danggeun.article.domain;

import org.hibernate.annotations.ColumnDefault;

import com.danggeun.article.enumerate.ArticleType;
import com.danggeun.commons.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Article extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer articleId;
	private Integer userId;
	private Integer commentId;
	private Integer regionId;
	private Integer groupId;
	private String subject;
	private String context;
	@Enumerated(EnumType.STRING)
	private ArticleType articleType;
	private Integer price;
	@ColumnDefault("true")
	@Column(columnDefinition = "TINYINT(1)")
	private boolean active;

	@Builder
	public Article(Integer articleId, Integer userId, Integer commentId, Integer regionId, Integer groupId,
		String subject,
		String context, ArticleType articleType, Integer price, boolean active) {
		this.articleId = articleId;
		this.userId = userId;
		this.commentId = commentId;
		this.regionId = regionId;
		this.groupId = groupId;
		this.subject = subject;
		this.context = context;
		this.articleType = articleType;
		this.price = price;
		this.active = active;
	}
}
