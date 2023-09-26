package com.danggeun.mail.dto;

import java.util.regex.Pattern;

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
	 * 이메일 값, 형식을 체크해서 Exception 던진다.
	 */
	public void validate() {
		String emailRegex = "^[0-9a-zA-Z]*@[a-zA-Z]+\\.[a-zA-Z]{2,6}$";

		if (this.getTo() == null)
			throw new EmailInvalidRequestException("이메일 값이 null입니다.");

		if (!Pattern.matches(emailRegex, this.getTo()))
			throw new EmailFormatException("이메일 형식이 맞지 않습니다.");
	}
}