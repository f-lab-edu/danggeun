package com.danggeun.mail.exception;

public class EmailInvalidRequestException extends RuntimeException {
	public EmailInvalidRequestException() {
	}

	public EmailInvalidRequestException(String message) {
		super(message);
	}
}
