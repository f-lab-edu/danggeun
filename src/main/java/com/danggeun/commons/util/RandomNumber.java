package com.danggeun.commons.util;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.Getter;

@Getter
public class RandomNumber {
	private String number;

	/**
	 * 랜덤 5자리 이메일 인증번호 생성
	 */
	public RandomNumber() {
		this.number = RandomStringUtils.randomNumeric(5);
	}

}
