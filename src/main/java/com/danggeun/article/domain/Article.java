package com.danggeun.article.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Article {

	private Integer articleId;
	private Integer userId;
	private Integer commentId;
	private Integer regionId;
	private Integer groupId;
	private String subject;
	private String context;
	private int articleType;
	private Integer price;
	private boolean active;
	private Date registeredDate;
	private String registeredId;
	private Date modifiedDate;
	private String modifiedId;
}
