package com.danggeun.commons.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Getter
@Service
public class CertificationNumber {
	private String number;

	public CertificationNumber() {
		this.number = RandomStringUtils.randomNumeric(5);
	}

}
