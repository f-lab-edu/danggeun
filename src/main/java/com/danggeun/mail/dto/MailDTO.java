package com.danggeun.mail.dto;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.danggeun.mail.exception.EmailFormatException;
import com.danggeun.mail.exception.EmailInvalidRequestException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailDTO {

	private String to;
	private String from;
	private String subject;
	private String text;
	private String certificationNumber;

	/**
	 * 사용자 이메일 값, 형식을 체크
	 */
	public void validate() {
		//이메일 값 체크
		validateEmail();
		// 이메일 형식 체크
		validateFromTo();
	}

	/**
	 * 사용자 이메일 값 체크
	 */
	private void validateEmail() {
		if (!StringUtils.hasText(this.getTo()))
			throw new EmailInvalidRequestException("이메일 값이 누락됐습니다.");
	}

	/**
	 * 사용자 이메일 형식 체크
	 */
	private void validateFromTo() {
		String emailRegex = "^[0-9a-zA-Z]*@[a-zA-Z]+\\.[a-zA-Z]{2,6}$";

		if (!Pattern.matches(emailRegex, this.getTo()))
			throw new EmailFormatException("이메일 형식이 맞지 않습니다.");
	}
}