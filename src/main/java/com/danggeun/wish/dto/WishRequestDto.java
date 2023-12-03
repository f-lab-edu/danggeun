package com.danggeun.wish.dto;

import java.time.LocalDateTime;

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
public class WishRequestDto {

	private Integer wishId;
	private boolean active;
	private Integer userId;
	private Integer articleId;
	private LocalDateTime registeredDate;
	private LocalDateTime modifiedDate;

	/**
	 * 관심목록 ID 값 존재 여부 확인
	 */
	public void validateId() {
		if (this.getWishId() == null) {
			throw new IllegalArgumentException("관심목록 ID가 없습니다.");
		}
	}

	public boolean hasId() {
		return this.getWishId() != null;
	}
}
