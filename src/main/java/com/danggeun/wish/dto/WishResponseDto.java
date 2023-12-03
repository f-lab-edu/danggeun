package com.danggeun.wish.dto;

import java.time.LocalDateTime;

import com.danggeun.wish.domain.Wish;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 관심목록 response 전용 Data Transfer Object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishResponseDto {

	public WishResponseDto(Wish wish) {
		this.wishId = wish.getWishId();
		this.active = wish.isActive();
		this.userId = wish.getUserId();
		this.articleId = wish.getArticle().getArticleId();
	}

	private Integer wishId;
	private boolean active;
	private Integer userId;
	private Integer articleId;
	private LocalDateTime registeredDate;
	private LocalDateTime modifiedDate;

}
