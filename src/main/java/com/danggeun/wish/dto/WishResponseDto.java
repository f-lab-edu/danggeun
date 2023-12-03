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
		this.wishId = wish.getId();
		this.active = wish.isActive();
	}

	private Integer wishId;
	private boolean active;
	private LocalDateTime registeredDate;
	private LocalDateTime modifiedDate;

}
