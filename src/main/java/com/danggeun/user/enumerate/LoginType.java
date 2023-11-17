package com.danggeun.user.enumerate;

public enum LoginType {
	/**
	 *	DEFALUT : 기본 로그인
	 *	NAVER : 네이버 로그인
	 *  KAKAO : 카카오 로그인
	 *  GOOGLE : 구글 로그인
	 */
	DEFAULT(1),
	NAVER(2),
	KAKAO(3),
	GOOGLE(4);

	private int typeValue;

	LoginType(int typeValue) {
		this.typeValue = typeValue;
	}

	public int getValue() {
		return this.typeValue;
	}
}
