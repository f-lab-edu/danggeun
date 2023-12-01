package com.danggeun.commons.config;

import java.util.LinkedHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.danggeun.commons.util.SessionConst;
import com.danggeun.user.repository.UserRepository;
import com.danggeun.user.service.CustomOAuth2Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomOAuth2Service customOAuth2Service;
	private final UserRepository userRepository;
	private final HttpSession httpSession;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.oauth2Login(login -> login
			.userInfoEndpoint(endpoint -> endpoint
				.userService(customOAuth2Service))
			.successHandler(successHandler())
		);
		return http.build();
	}

	/**
	 * OAuth2 인증 성공 후
	 */
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return ((request, response, authentication) -> {
			DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User)authentication.getPrincipal();
			Long id;
			String provider;
			if (defaultOAuth2User.getAttributes().get("id") != null) {
				provider = "KAKAO";
				LinkedHashMap<String, String> map = (LinkedHashMap)defaultOAuth2User.getAttributes()
					.get("kakao_account");
				id = findByEmailWithLoginType(map.get("email"), provider);
				setAttribute(id, request);
			} else if (defaultOAuth2User.getAttributes().get("sub") != null) {
				provider = "GOOGLE";
				id = findByEmailWithLoginType(defaultOAuth2User.getAttributes().get("email").toString(), provider);
				setAttribute(id, request);
			} else if (defaultOAuth2User.getAttributes().get("response") != null) {
				provider = "NAVER";
				LinkedHashMap<String, String> map = (LinkedHashMap)defaultOAuth2User.getAttributes().get("response");
				id = findByEmailWithLoginType(map.get("email"), provider);
				setAttribute(id, request);
			}
		});
	}

	/**
	 * 사용자 LoginType, email 조회
	 * @param email
	 * @param provider
	 * @return
	 */
	private Long findByEmailWithLoginType(String email, String provider) {
		return userRepository.findByEmailWithLoginType(email, provider).get().getUserId();
	}

	/**
	 * 세션 저장
	 * @param id
	 * @param request
	 */
	private void setAttribute(Long id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(SessionConst.LOGIN_USER, id);
	}
}
