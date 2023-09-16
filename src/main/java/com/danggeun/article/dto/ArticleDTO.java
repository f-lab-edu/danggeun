package com.danggeun.article.dto;

import java.util.Date;

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

	private String articleId;
	private String userId;
	private String commentId;
	private String regionId;
	private String groupId;
	private String subject;
	private String context;
	private ArticleType articleType;
	private int price;
	private boolean active;
	private Date regDt;
	private String regId;
	private Date modDt;
	private String modId;

}
