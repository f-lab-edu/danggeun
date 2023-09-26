package com.danggeun.commons.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.danggeun.mail.exception.CertificationNumberMismatchException;
import com.danggeun.mail.exception.EmailDuplicatedException;
import com.danggeun.mail.exception.EmailFormatException;
import com.danggeun.mail.exception.EmailInvalidRequestException;
import com.danggeun.mail.exception.NoCertificationException;
import com.danggeun.user.exception.NicknameDuplicatedException;
import com.danggeun.user.exception.UserInvalidRequestException;
import com.danggeun.user.exception.UserPasswordFormatException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public String exceptionHandler(RuntimeException e) {
		log.error("Exception {} catch : {}", e.getClass().getSimpleName(), e.fillInStackTrace());
		return e.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(EmailDuplicatedException.class)
	public String userDuplicatedExceptionHandler(EmailDuplicatedException e) {
		log.error("이미 가입된 유저입니다.", e.fillInStackTrace());
		return e.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NicknameDuplicatedException.class)
	public String nicknameDuplicatedExceptionHandler(NicknameDuplicatedException e) {
		log.error("닉네임이 중복됩니다.", e.fillInStackTrace());
		return e.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UserInvalidRequestException.class)
	public String userInvalidRequestExceptionHandler(UserInvalidRequestException e) {
		log.error("회원 정보 중 null 값이 존재합니다.", e.fillInStackTrace());
		return e.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UserPasswordFormatException.class)
	public String userPasswordFormatExceptionHandler(UserPasswordFormatException e) {
		log.error("최소 한개 이상의 대소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호를 입력해야 합니다.");
		return e.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(EmailInvalidRequestException.class)
	public String emailInvalidRequestExceptionHandler(EmailInvalidRequestException e) {
		log.error("이메일 값이 null입니다.", e.fillInStackTrace());
		return e.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(EmailFormatException.class)
	public String emailFormatException(EmailFormatException e) {
		log.error("이메일 형식이 맞지 않습니다.", e.fillInStackTrace());
		return e.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CertificationNumberMismatchException.class)
	public String certificationNumberMismatchExceptionHandler(
		CertificationNumberMismatchException e) {
		log.error("인증번호가 일치하지 않습니다.", e.fillInStackTrace());
		return e.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NoCertificationException.class)
	public String noCertificationExceptionHandler(NoCertificationException e) {
		log.error("이메일 인증하지 않았습니다.", e.fillInStackTrace());
		return e.getMessage();
	}

}