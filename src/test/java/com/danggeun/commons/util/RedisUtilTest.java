package com.danggeun.commons.util;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisUtilTest {

	@Autowired
	RedisUtil redisUtil;

	@Test
	void 레디스이메일인증번호조회() {
		//given
		String email = "test@test.com";
		String code = "111";
		long expire = 60;
		//when
		redisUtil.setDataExpire(email, code, expire);
		//then
		Assertions.assertThat(redisUtil.getData(email)).isEqualTo(code);
	}

	@Test
	void 레디스이메일인증번호존재여부조회() {
		//given
		String email = "test2@test.com";
		String code = "222";
		long expire = 60;
		//when
		redisUtil.setDataExpire(email, code, expire);
		//then
		Assertions.assertThat(redisUtil.existData(email)).isTrue();
	}

	@Test
	void 레디스이메일인증번호삭제(){
		//given
		String email = "test3@test.com";
		String code = "333";
		long expire = 60;
		//when
		redisUtil.setDataExpire(email, code, expire);
		//then
		redisUtil.deleteData(email);
		Assertions.assertThat(redisUtil.existData(email)).isFalse();
	}

}