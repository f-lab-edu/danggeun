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
import com.danggeun.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		// 사용자 가입 필수 데이터 중 null 값이 있는지, 비밀번호 형식이 맞는지 체크
		userDTO.validate();

		return new ResponseEntity<>(userService.join(userDTO), HttpStatus.CREATED);
	}

	@GetMapping("/check/email/{email}")
	public ResponseEntity<String> isDuplicatedEmail(@PathVariable String email) {
		// 가입된 사용자 이메일 중복 체크
		userService.isDuplicatedEmail(email);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/check/nickname/{nickname}")
	public ResponseEntity<String> isDuplicatedNickname(@PathVariable String nickname) {
		// 가입된 사용자 닉네임 중복 체크
		userService.isDuplicatedNickname(nickname);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
