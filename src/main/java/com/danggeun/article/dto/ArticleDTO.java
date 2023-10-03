package com.danggeun.article.dto;

import java.util.Date;
import java.util.Objects;

import org.springframework.util.StringUtils;

import com.danggeun.article.enumerate.ArticleType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {

	private Integer articleId;
	private Integer userId;
	private Integer commentId;
	private Integer regionId;
	private Integer groupId;
	private String subject;
	private String context;
	private ArticleType articleType;
	private int price;
	private boolean active;
	private Date registeredDate;
	private String registeredId;
	private Date modifiedDate;
	private String modifiedId;

	public void hasId() {
		if (Objects.isNull(this.getArticleId())) {
			throw new IllegalArgumentException("게시글 ID가 없습니다.");
		}
	}

	public void validateArticleNullable() {
		if (Objects.isNull(this.getUserId())
			|| Objects.isNull(this.getRegionId())
			|| !StringUtils.hasText(this.getSubject())
			|| !StringUtils.hasText(this.getContext())
		) {
			throw new IllegalArgumentException("게시글 필수값 NULL 입니다.");
		}

		switch (this.getArticleType()) {
			case NORMAL:
				break;
			case GROUP:
				if (Objects.isNull(this.getGroupId())) {
					throw new IllegalArgumentException("게시글 필수값 NULL 입니다.");
				}
				break;
			case TRADE:
				if (Objects.isNull(this.getPrice())) {
					throw new IllegalArgumentException("게시글 필수값 NULL 입니다.");
				}
				break;
			default:
				throw new IllegalArgumentException("정상적인 게시글 타입이 아닙니다.");
		}

	}

}
