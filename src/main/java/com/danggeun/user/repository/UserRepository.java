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
	 * 사용자 수정
	 * @param userDTO
	 * @return UserDTO
	 */
	public int update(UserDTO userDTO);

	/**
	 * 사용자 Email 조회
	 * @param email
	 * @return Optional<UserDTO>
	 */
	public Optional<UserDTO> findByEmail(String email);

	/**
	 * 사용자 로그인 유형, Email 조회
	 * @param email
	 * @param loginType
	 * @return
	 */
	public Optional<UserDTO> findByEmailWithLoginType(String email, String loginType);

	/**
	 * 사용자 Nickname 조회
	 * @param nickname
	 * @return Optional<UserDTO>
	 */
	public Optional<UserDTO> findByNickname(String nickname);

}