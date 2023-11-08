package com.danggeun.commons.util;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailRedisService {
	private final StringRedisTemplate stringRedisTemplate;

	/**
	 * redis 데이터 조회
	 * @param key
	 * @return value
	 */
	public String get(String key) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		return valueOperations.get(key);
	}

	/**
	 * redis 데이터 존재 여부 조회
	 * @param key
	 * @return boolean
	 */
	public boolean exist(String key) {
		return stringRedisTemplate.hasKey(key);
	}

	/**
	 * redis 데이터 생성
	 * @param key, value
	 */
	public void set(String key, String value) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		valueOperations.set(key, value);
	}

	/**
	 * redis 데이터, 만료시간 생성
	 * @param key, value, duration
	 */
	public void set(String key, String value, long duration) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		Duration expireDuration = Duration.ofSeconds(duration);
		valueOperations.set(key, value, expireDuration);
	}

	/**
	 * redis 데이터 삭제
	 * @param key
	 */
	public void delete(String key) {
		stringRedisTemplate.delete(key);
	}
}
