package com.danggeun.user.exception;

public class UserIncorrectResultSizeException extends RuntimeException {
	public UserIncorrectResultSizeException() {
	}

	public UserIncorrectResultSizeException(String message) {
		super(message);
	}
}
