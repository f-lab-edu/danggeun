package com.danggeun.article.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Article {

	private String article_id;
	private String user_id;
	private String comment_id;
	private String region_id;
	private String group_id;
	private String subject;
	private String context;
	private int article_gb;
	private int price;
	private boolean active;
	private String reg_dt;
	private String reg_id;
	private String mod_dt;
	private String mod_id;
}
