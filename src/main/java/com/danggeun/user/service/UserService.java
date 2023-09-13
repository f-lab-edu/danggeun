package com.danggeun.user.service;

import org.springframework.stereotype.Service;

import com.danggeun.user.dto.User;
import com.danggeun.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public void join(User user){
		userRepository.save(user);
	}
}