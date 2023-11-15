package com.danggeun.user.dto;

import java.util.Map;

public class NaverOAuth2UserDto extends OAuth2UserInfo {
	public NaverOAuth2UserDto(Map<String, Object> attributes) {
		super((Map<String, Object>)attributes.get("response"));
	}

	@Override
	public String getOAuth2Id() {
		return (String)attributes.get("id");
	}

	@Override
	public String getEmail() {
		return (String)attributes.get("email");
	}

	@Override
	public String getName() {
		return (String)attributes.get("name");
	}

}
