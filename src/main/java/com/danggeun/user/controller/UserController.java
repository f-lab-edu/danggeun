package com.danggeun.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danggeun.user.dto.UserDTO;
import com.danggeun.user.service.LoginService;
import com.danggeun.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	private final UserService userService;

	/**
	 * 사용자 생성
	 * @param userDTO
	 * @return UserDTO
	 */
	@PostMapping()
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {

		return new ResponseEntity<>(userService.join(userDTO), HttpStatus.CREATED);
	}

	@GetMapping("/duplicated/email/{email}")
	public ResponseEntity isDuplicatedEmail(@PathVariable String email) {
		// 가입된 사용자 이메일 중복 체크
		userService.isDuplicatedEmail(email);

		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/duplicated/nickname/{nickname}")
	public ResponseEntity isDuplicatedNickname(@PathVariable String nickname) {
		// 가입된 사용자 닉네임 중복 체크
		userService.isDuplicatedNickname(nickname);

		return new ResponseEntity(HttpStatus.OK);
	}
}
