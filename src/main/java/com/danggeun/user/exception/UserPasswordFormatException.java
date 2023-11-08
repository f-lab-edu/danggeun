package com.danggeun.user.exception;

public class UserPasswordFormatException extends RuntimeException {
	public UserPasswordFormatException() {
	}

	public UserPasswordFormatException(String message) {
		super(message);
	}
}
