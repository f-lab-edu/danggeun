package com.danggeun.user.exception;

public class NicknameDuplicatedException extends RuntimeException {
	public NicknameDuplicatedException() {
	}

	public NicknameDuplicatedException(String message) {
		super(message);
	}
}
