package com.danggeun.user.dto;

import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.danggeun.user.enumerate.LoginType;
import com.danggeun.user.exception.UserInvalidRequestException;
import com.danggeun.user.exception.UserPasswordFormatException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {

	private long userId;
	private String userName;
	private String userPassword;
	private String userNickname;
	private String userEmail;
	private String userPhoneNumber;
	private LoginType userLoginType;
	private String userAccessToken;
	private String userRefreshToken;
	private boolean userActive;
	private boolean userEmailCertificationFlag;
	private String createId;
	private Date createdDate;
	private String modifiedId;
	private Date modifiedDate;
	private Double longitude; // x
	private Double latitude;  // y
	private String representRegionStatus; // 대표 지역
	private String regionRangeStatus; // 지역 범위

	/**
	 * 사용자 가입 전 필수 데이터 중 null값이 있는지,
	 * 최소 한개 이상의 대소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호인지 검사한다.
	 */
	public void validate() {

		// 사용자 정보 필수 데이터 체크
		validateUserNullable();

		// 사용자 패스워드 형식 체크
		validateFromPassword();
	}

	/**
	 * 사용자 정보 필수 데이터 체크
	 */
	private void validateUserNullable() {
		if (!StringUtils.hasText(this.getUserName()) || !StringUtils.hasText(this.getUserNickname())
			|| !StringUtils.hasText(this.getUserEmail()))
			throw new UserInvalidRequestException("사용자 정보가 누락됐습니다.");

		switch (this.getUserLoginType()) {
			case DEFAULT, NAVER, KAKAO, GOOGLE:
				break;
			default:
				throw new UserInvalidRequestException("사용자 정보가 누락됐습니다.");
		}
	}

	/**
	 * 사용자 패스워드 형식 체크
	 */
	private void validateFromPassword() {
		String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#!~$%^&-+=()])[A-Za-z0-9$@!%*?&]{8,16}$";

		if (!Pattern.matches(passwordRegex, this.getUserPassword()))
			throw new UserPasswordFormatException("최소 한개 이상의 대소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호를 입력해야 합니다.");
	}
}
