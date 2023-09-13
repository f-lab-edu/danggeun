package com.danggeun.mail.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Mail {

	@Email
	@NotBlank
	private String to;
	private String from;
	private String subject;
	private String text;
    @Positive
	private String certificationNumber;
}