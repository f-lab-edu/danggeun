package com.danggeun.user.repository;

import java.util.Optional;

import com.danggeun.user.dto.UserDTO;

public interface UserRepository {

	/**
	 * 사용자 생성
	 * @param userDTO
	 * @return UserDTO
	 */
	public UserDTO save(UserDTO userDTO);

	/**
	 * 사용자 Email 조회
	 * @param email
	 * @return Optional<UserDTO>
	 */
	public Optional<UserDTO> findByEmail(String email);

	/**
	 * 사용자 Nickname 조회
	 * @param nickname
	 * @return Optional<UserDTO>
	 */
	public Optional<UserDTO> findByNickname(String nickname);
}