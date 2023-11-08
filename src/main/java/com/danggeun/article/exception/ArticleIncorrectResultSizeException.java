package com.danggeun.article.exception;

public class ArticleIncorrectResultSizeException extends RuntimeException {
	public ArticleIncorrectResultSizeException() {
	}

	public ArticleIncorrectResultSizeException(String message) {
		super(message);
	}

	public ArticleIncorrectResultSizeException(String message, Throwable cause) {
		super(message, cause);
	}
}
