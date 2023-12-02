package com.danggeun.mail.exception;

public class EmailDuplicatedException extends RuntimeException {
	public EmailDuplicatedException() {
	}

	public EmailDuplicatedException(String message) {
		super(message);
	}
}
