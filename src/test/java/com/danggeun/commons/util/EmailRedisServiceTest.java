package com.danggeun.commons.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailRedisServiceTest {

	@Autowired
	EmailRedisService emailRedisService;

	@Test
	void 레디스이메일인증번호조회() {
		//given
		String email = "test@test.com";
		String code = "111";
		long expire = 60;
		//when
		emailRedisService.set(email, code, expire);
		//then
		Assertions.assertThat(emailRedisService.get(email)).isEqualTo(code);
	}

	@Test
	void 레디스이메일인증번호존재여부조회() {
		//given
		String email = "test2@test.com";
		String code = "222";
		long expire = 60;
		//when
		emailRedisService.set(email, code, expire);
		//then
		Assertions.assertThat(emailRedisService.exist(email)).isTrue();
	}

	@Test
	void 레디스이메일인증번호삭제(){
		//given
		String email = "test3@test.com";
		String code = "333";
		long expire = 60;
		//when
		emailRedisService.set(email, code, expire);
		//then
		emailRedisService.delete(email);
		Assertions.assertThat(emailRedisService.exist(email)).isFalse();
	}

}