package com.danggeun.article.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Article {

	private String articleId;
	private String userId;
	private String commentId;
	private String regionId;
	private String groupId;
	private String subject;
	private String context;
	private int articleType;
	private int price;
	private boolean active;
	private Date regDt;
	private String regId;
	private Date modDt;
	private String modId;
}
