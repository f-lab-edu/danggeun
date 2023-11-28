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
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Article extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_id")
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

}
