package com.danggeun.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danggeun.article.service.ArticleService;

@RestController
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@GetMapping("/article/count")
	public String articleCount() {
		StringBuffer st = new StringBuffer("ok");
		int count = 0;

		count = articleService.articleCount();
		
		st.append("\n");
		st.append(count + "");

		return st.toString();
	}
}
