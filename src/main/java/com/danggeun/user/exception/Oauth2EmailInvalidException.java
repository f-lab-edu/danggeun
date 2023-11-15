package com.danggeun.user.exception;

public class Oauth2EmailInvalidException extends RuntimeException {
	public Oauth2EmailInvalidException() {
	}

	public Oauth2EmailInvalidException(String message) {
		super(message);
	}
}
