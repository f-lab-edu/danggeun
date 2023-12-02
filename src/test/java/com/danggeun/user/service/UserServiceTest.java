package com.danggeun.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.danggeun.user.dto.UserDTO;
import com.danggeun.user.enumerate.LoginType;
import com.danggeun.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootTest
class UserServiceTest {

	UserService userService;
	UserRepository userRepository;
	PasswordEncoder passwordEncoder;

	UserDTO userDTO = null;

	@BeforeEach
	void setUp() {
		userDTO = new UserDTO(100000, "testName", "1234", "testNickName", "test.test.com", "010-1111-1111",
			LoginType.DEFAULT, null,
			null, true, true, "testCreatedId", null, "testModifiedId", null, 1.1, 1.1, "1", "1");
	}

	@Test
	@Transactional
	@Rollback
	@DisplayName("사용자 신규 등록")
	void userJoin() {
		//given : @BeforeEach에서 처리

		//when
		UserDTO resultDTO = userService.join(userDTO);

		//then
		assertThat(resultDTO.getUserId()).isEqualTo(
			userRepository.findByEmail(resultDTO.getUserEmail()).get().getUserId());

	}

	@Test
	@Transactional
	@Rollback
	@DisplayName("비밀번호 암호화")
	void userPasswordEncoder() {
		//given : @BeforeEach에서 처리

		//when
		UserDTO resultDTO = userService.join(userDTO);
		String encodePw = passwordEncoder.encode(resultDTO.getUserPassword());

		//then
		assertThat(
			passwordEncoder.matches(userRepository.findByEmail(resultDTO.getUserEmail()).get().getUserPassword(),
				encodePw)).isTrue();
	}
}