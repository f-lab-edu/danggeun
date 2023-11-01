package com.danggeun.wish.dto;

import java.time.LocalDateTime;

import com.danggeun.article.dto.ArticleResponseDto;

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

	private Long wishId;
	private boolean active;
	private LocalDateTime registeredDate;
	private Long registeredId;
	private LocalDateTime modifiedDate;
	private Long modifiedId;

	private ArticleResponseDto articleDto;

}
