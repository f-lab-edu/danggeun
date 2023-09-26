package com.danggeun.user.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danggeun.user.dto.UserDTO;
import com.danggeun.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	private final UserService userService;

	/**
	 * 회원 생성
	 * @param userDTO
	 * @return UserDTO
	 */
	@PostMapping()
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws NoSuchAlgorithmException {

		return new ResponseEntity<>(userService.join(userDTO), HttpStatus.CREATED);
	}
}
