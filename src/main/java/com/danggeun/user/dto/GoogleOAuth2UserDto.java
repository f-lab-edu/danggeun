package com.danggeun.user.dto;

import java.util.Map;

public class GoogleOAuth2UserDto extends OAuth2UserInfo {
	public GoogleOAuth2UserDto(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getOAuth2Id() {
		return (String)attributes.get("sub");
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
