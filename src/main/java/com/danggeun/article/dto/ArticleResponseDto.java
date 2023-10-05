package com.danggeun.article.dto;

import java.util.Date;

import com.danggeun.article.enumerate.ArticleType;

import lombok.Data;

/**
 * 게시글 response 전용 Data Transfer Object
 */
@Data
public class ArticleResponseDto {

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

}
