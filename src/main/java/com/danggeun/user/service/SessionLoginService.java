package com.danggeun.user.service;

import org.springframework.stereotype.Service;

import com.danggeun.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {
	private static final String LOGIN_USER = "loginUser";

	@Override
	public void login(UserDTO userDTO, HttpServletRequest request) {
		//로그인 성공 처리
		//세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
		HttpSession session = request.getSession();
		//세션에 로그인 userId 보관
		session.setAttribute(LOGIN_USER, userDTO.getUserId());
	}

	@Override
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			//session 제거
			session.invalidate();
		}
	}
}
