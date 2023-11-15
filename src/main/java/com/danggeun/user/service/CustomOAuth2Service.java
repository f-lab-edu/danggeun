package com.danggeun.user.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.danggeun.user.dto.OAuth2UserInfo;
import com.danggeun.user.dto.OAuth2UserInfoFactory;
import com.danggeun.user.dto.UserDTO;
import com.danggeun.user.enumerate.LoginType;
import com.danggeun.user.exception.Oauth2EmailInvalidException;
import com.danggeun.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * 인증 성공한 사용자 정보 조회
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2Service extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

		OAuth2User oAuth2User = delegate.loadUser(oAuth2UserRequest); // OAuth2 정보를 가져옵니다.

		//Role generate
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");

		String userNameAttributeName = oAuth2UserRequest.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();

		processOAth2User(oAuth2UserRequest, oAuth2User);

		return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), userNameAttributeName);
	}

	private void processOAth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest,
			oAuth2User.getAttributes());

		if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
			throw new Oauth2EmailInvalidException("OAuth2 이메일이 없습니다.");
		}

		UserDTO userDTO = userRepository.findByEmailWithLoginType(oAuth2UserInfo.getEmail(),
			oAuth2UserRequest.getClientRegistration()
				.getRegistrationId()
				.toUpperCase()).orElse(null);

		// Dangguen 서비스에 이미 가입되어 있는지 확인
		if (userDTO != null && oAuth2UserRequest.getClientRegistration()
			.getRegistrationId()
			.toUpperCase()
			.equals(userDTO.getLoginType().toString())) {
			userRepository.update(userDTO);
		} else {
			registerUsers(oAuth2UserRequest, oAuth2UserInfo);
		}
	}

	private void registerUsers(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
		UserDTO userDTO = UserDTO.builder()
			.userName(oAuth2UserInfo.getName())
			.email(oAuth2UserInfo.getEmail())
			.loginType(LoginType.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase()))
			.active(true)
			.build();
		userRepository.save(userDTO);
	}
}