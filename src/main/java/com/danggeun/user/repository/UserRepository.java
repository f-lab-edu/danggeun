package com.danggeun.user.repository;

import com.danggeun.user.dto.User;

public interface UserRepository {
	public int save(User user);
}