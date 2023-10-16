package com.danggeun.commons.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.danggeun.article.exception.ArticleIncorrectResultSizeException;
import com.danggeun.article.exception.ArticleNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public String exceptionHandler(RuntimeException e) {
		log.error("Exception {} catch : {}", e.getClass().getSimpleName(), e.fillInStackTrace());
		return e.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({IllegalArgumentException.class, NoSuchFieldException.class})
	public String handler(IllegalArgumentException e) {
		log.error("Exception {} catch : {}", e.getClass().getSimpleName(), e.fillInStackTrace());
		return e.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ArticleNotFoundException.class)
	public String handler(ArticleNotFoundException e) {
		log.error("Exception {} catch : {}", e.getClass().getSimpleName(), e.fillInStackTrace());
		return e.getMessage();
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ArticleIncorrectResultSizeException.class)
	public String handler(ArticleIncorrectResultSizeException e) {
		log.error("Exception {} catch : {}", e.getClass().getSimpleName(), e.fillInStackTrace());
		return e.getMessage();
	}

}
