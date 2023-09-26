package com.danggeun.user.dto;

import java.util.Date;
import java.util.regex.Pattern;

import com.danggeun.user.exception.UserInvalidRequestException;
import com.danggeun.user.exception.UserPasswordFormatException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {

	private String userId;
	private String userName;
	private String userPassword;
	private String userNickname;
	private String userEmail;
	private String userPhoneNumber;
	private String userLoginType;
	private String userAccessToken;
	private String userRefreshToken;
	private boolean userActive;
	private boolean userEmailCertificationFlag;
	private String createId;
	private Date createdDate;
	private String modifiedId;
	private Date modifiedDate;

	/**
	 * 회원가입 전 필수 데이터중 null값이 있는지,
	 * 최소 한개 이상의 대소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호인지 검사한다.
	 */
	public void validate() {
		String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#!~$%^&-+=()])[A-Za-z0-9$@!%*?&]{8,16}$";

		if (this.getUserName() == null || this.getUserNickname() == null
			|| this.getUserEmail() == null)
			throw new UserInvalidRequestException("회원 정보 중 null 값이 존재합니다.");

		if (!Pattern.matches(passwordRegex, this.getUserPassword()))
			throw new UserPasswordFormatException("최소 한개 이상의 대소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호를 입력해야 합니다.");
	}
}