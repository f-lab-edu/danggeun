package com.danggeun.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.danggeun.user.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Primary
@Repository
public class JdbcTemplateUserRepository implements UserRepository {

	private final JdbcTemplate jdbcTemplate;

	/**
	 * 회원 생성
	 * @param userDTO
	 * @return UserDTO
	 */
	@Override
	public UserDTO save(UserDTO userDTO) {
		userDTO.setUserLoginType("D");
		StringBuilder query = new StringBuilder();
		query.append("insert into ")
			.append("user(user_password, user_name, nickname, email, phone_number, login_type, "
				+ "active, created_date, created_id, modified_date, modified_id) ")
			.append("values (?, ?, ?, ?, ?, ?, ?, now(), ?, "
				+ "now(), ?)");
		jdbcTemplate.update(query.toString(), userDTO.getUserPassword(), userDTO.getUserName()
			, userDTO.getUserNickname(), userDTO.getUserEmail(), userDTO.getUserPhoneNumber()
			, userDTO.getUserLoginType(), userDTO.isUserActive(), userDTO.getCreateId(), userDTO.getModifiedId());
		return userDTO;
	}

	/**
	 * 회원 Email 조회
	 * @param email
	 * @return Optional<UserDTO>
	 */
	@Override
	public Optional<UserDTO> findByEmail(String email) {
		StringBuilder query = new StringBuilder();
		query.append("select * from user where email = ?");

		List<UserDTO> result = jdbcTemplate.query(query.toString(), itemRowMapper(), email);

		return result.stream().findFirst();
	}

	/**
	 * 회원 Nickname 조회
	 * @param nickname
	 * @return Optional<UserDTO>
	 */
	@Override
	public Optional<UserDTO> findByNickname(String nickname) {
		StringBuilder query = new StringBuilder();
		query.append("select * from user where nickname = ?");

		List<UserDTO> result = jdbcTemplate.query(query.toString(), itemRowMapper(), nickname);

		return result.stream().findFirst();
	}

	private RowMapper<UserDTO> itemRowMapper() {
		return (rs, rowNum) -> {
			UserDTO userDTO = new UserDTO();

			userDTO.setUserId(rs.getString("user_id"));
			userDTO.setUserName(rs.getString("user_name"));
			userDTO.setUserPassword(rs.getString("user_password"));
			userDTO.setUserNickname(rs.getString("nickname"));
			userDTO.setUserEmail(rs.getString("email"));
			userDTO.setUserPhoneNumber(rs.getString("phone_number"));
			userDTO.setUserLoginType(rs.getString("login_type"));
			userDTO.setUserAccessToken(rs.getString("access_token"));
			userDTO.setUserRefreshToken(rs.getString("refresh_token"));
			userDTO.setUserActive(rs.getBoolean("active"));
			userDTO.setCreateId(rs.getString("created_id"));
			userDTO.setCreatedDate(rs.getDate("created_date"));
			userDTO.setModifiedId(rs.getString("modified_id"));
			userDTO.setModifiedDate(rs.getDate("modified_date"));

			return userDTO;
		};
	}
}