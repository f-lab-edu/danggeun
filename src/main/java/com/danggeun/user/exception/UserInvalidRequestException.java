package com.danggeun.user.exception;

public class UserInvalidRequestException extends RuntimeException {
	public UserInvalidRequestException() {
	}

	public UserInvalidRequestException(String message) {
		super(message);
	}
}
