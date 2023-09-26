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

	public String get(String key) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		return valueOperations.get(key);
	}

	public boolean exist(String key) {
		return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
	}

	public void set(String key, String value) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		valueOperations.set(key, value);
	}

	public void set(String key, String value, long duration) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		Duration expireDuration = Duration.ofSeconds(duration);
		valueOperations.set(key, value, expireDuration);
	}

	public void delete(String key) {
		stringRedisTemplate.delete(key);
	}
}
