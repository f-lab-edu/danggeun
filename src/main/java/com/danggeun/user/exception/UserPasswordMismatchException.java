package com.danggeun.user.exception;

public class UserPasswordMismatchException extends RuntimeException {
	public UserPasswordMismatchException() {
	}

	public UserPasswordMismatchException(String message) {
		super(message);
	}
}
