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
//@RequiredArgsConstructor
public class JdbcTemplateUserRepository implements UserRepository {

	//private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
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
			.addValue("nickname", userDTO.getUserNickname())
			.addValue("email", userDTO.getUserEmail())
			.addValue("phone_number", userDTO.getUserPhoneNumber())
			.addValue("login_type", userDTO.getUserLoginType().getValue())
			.addValue("active", userDTO.isUserActive())
			.addValue("created_id", userDTO.getCreateId())
			.addValue("modified_id", userDTO.getModifiedId());
		Number key = simpleJdbcInsert.executeAndReturnKey(param);
		userDTO.setUserId(key.longValue());
		return userDTO;
	}

	/**
	 * 사용자 Email 조회
	 * @param email
	 * @return Optional<UserDTO>
	 */
	@Override
	public Optional<UserDTO> findByEmail(String email) {
		Map<String, Object> param = Map.of("email", email);
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