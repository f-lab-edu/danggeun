package com.danggeun.article.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int articleCount() {
		return jdbcTemplate.queryForObject("select count(*) from article", Integer.class);
	}
}
