package com.danggeun.commons.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.danggeun.user.exception.NaverLoginException;
import com.danggeun.user.exception.Oauth2EmailInvalidException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Oauth2EmailInvalidException.class)
	public String oauth2EmailInvalidException(NaverLoginException e) {
		log.error("OAuth2 이메일이 없습니다.", e.fillInStackTrace());
		return e.getMessage();
	}
}