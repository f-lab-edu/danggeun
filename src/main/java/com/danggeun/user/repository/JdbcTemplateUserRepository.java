package com.danggeun.user.repository;

import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.danggeun.user.dto.UserDTO;
import com.danggeun.user.exception.UserIncorrectResultSizeException;

@Primary
@Repository
public class JdbcTemplateUserRepository implements UserRepository {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert simpleJdbcInsert;

	public JdbcTemplateUserRepository(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("user")
			.usingGeneratedKeyColumns("user_id")
			.usingColumns("user_password", "user_name", "nickname", "email", "phone_number", "login_type", "active",
				"created_id", "modified_id");
	}

	/**
	 * 사용자 생성
	 * @param userDTO
	 * @return UserDTO
	 */
	@Override
	public UserDTO save(UserDTO userDTO) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("user_password", userDTO.getUserPassword())
			.addValue("user_name", userDTO.getUserName())
			.addValue("nickname", userDTO.getNickname())
			.addValue("email", userDTO.getEmail())
			.addValue("phone_number", userDTO.getPhoneNumber())
			.addValue("login_type", userDTO.getLoginType())
			.addValue("active", userDTO.isActive())
			.addValue("created_id", userDTO.getCreateId())
			.addValue("modified_id", userDTO.getModifiedId());
		Number key = simpleJdbcInsert.executeAndReturnKey(param);
		userDTO.setUserId(key.longValue());
		return userDTO;
	}

	@Override
	public int update(UserDTO userDTO) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("user_name", userDTO.getUserName())
			.addValue("nickname", userDTO.getNickname())
			.addValue("email", userDTO.getEmail())
			.addValue("phone_number", userDTO.getPhoneNumber())
			.addValue("active", userDTO.isActive())
			.addValue("created_id", userDTO.getCreateId())
			.addValue("modified_id", userDTO.getModifiedId())
			.addValue("login_type", userDTO.getLoginType().toString());
		StringBuilder query = new StringBuilder();
		query.append(
			"update user set "
				+ "user_name = :user_name, nickname = :nickname, email = :email, "
				+ "phone_number = :phone_number, active = :active, "
				+ "created_id = :created_id, modified_id = :modified_id "
				+ "where email = :email ");
		query.append("and login_type = :login_type");

		return namedParameterJdbcTemplate.update(query.toString(), param);
	}

	/**
	 * 사용자 Email 조회
	 * @param email
	 * @return Optional<UserDTO>
	 */
	@Override
	public Optional<UserDTO> findByEmail(String email) {
		Map<String, Object> param = Map.of(
			"email", email
		);
		StringBuilder query = new StringBuilder();
		query.append("select * from user where email = :email");

		try {
			UserDTO result = namedParameterJdbcTemplate.queryForObject(query.toString(), param, UserRowMapper());
			return Optional.ofNullable(result);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new UserIncorrectResultSizeException("조회한 결과가 2건 이상입니다.");
		}
	}

	/**
	 * 사용자 Email 조회
	 * @param email
	 * @return Optional<UserDTO>
	 */
	@Override
	public Optional<UserDTO> findByEmailWithLoginType(String email, String loginType) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("email", email)
			.addValue("login_type", loginType);

		StringBuilder query = new StringBuilder();
		query.append("select * from user where email = :email ");
		query.append("and login_type = :login_type");

		try {
			UserDTO result = namedParameterJdbcTemplate.queryForObject(query.toString(), param, UserRowMapper());
			return Optional.ofNullable(result);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new UserIncorrectResultSizeException("조회한 결과가 2건 이상입니다.");
		}
	}

	/**
	 * 사용자 Nickname 조회
	 * @param nickname
	 * @return Optional<UserDTO>
	 */
	@Override
	public Optional<UserDTO> findByNickname(String nickname) {
		StringBuilder query = new StringBuilder();
		query.append("select * from user where nickname = :nickname");
		Map<String, Object> param = Map.of("nickname", nickname);
		try {

			UserDTO result = namedParameterJdbcTemplate.queryForObject(query.toString(), param, UserRowMapper());
			return Optional.ofNullable(result);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new UserIncorrectResultSizeException("조회한 결과가 2건 이상입니다.");
		}
	}

	private RowMapper<UserDTO> UserRowMapper() {
		return BeanPropertyRowMapper.newInstance(UserDTO.class);
	}
}