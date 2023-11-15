package com.danggeun.user.dto;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(OAuth2UserRequest oAuth2UserRequest,
		Map<String, Object> attributes) {
		switch (oAuth2UserRequest.getClientRegistration().getRegistrationId()) {
			case "google":
				return new GoogleOAuth2UserDto(attributes);
			case "naver":
				return new NaverOAuth2UserDto(attributes);
			case "kakao":
				return new KakaoOAuth2UserDto(attributes);
			default:
				throw new IllegalArgumentException("Invalid Provider Type");
		}
	}
}
