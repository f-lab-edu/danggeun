package com.danggeun.user.service;

import com.danggeun.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface LoginService {
	public void login(UserDTO userDTO, HttpServletRequest request);

	public void logout(HttpServletRequest request);
}
