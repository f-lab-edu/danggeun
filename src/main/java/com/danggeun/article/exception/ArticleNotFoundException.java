package com.danggeun.article.exception;

public class ArticleNotFoundException extends RuntimeException {
	public ArticleNotFoundException() {
	}

	public ArticleNotFoundException(String message) {
		super(message);
	}

	public ArticleNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
