package com.danggeun.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class RedisConfig {
	@Bean
	public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
		//redis-cli에서 세션에 담긴 정보들을 보기 위해 아래와 같이 직렬화 빈을 생성합니다.
		return new GenericJackson2JsonRedisSerializer();
	}
}
