package com.danggeun.user.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.danggeun.user.dto.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Primary
@Repository
public class JdbcUserRepository implements UserRepository{

	private final JdbcTemplate jdbcTemplate;

	@Override
	public int save(User user) {
		user.setUserLoginType("D");
		user.setUserActive(1);
		StringBuilder query = new StringBuilder();
		String sequence_next_sql = "(SELECT CONCAT('USER', LPAD(nextval('user_sequence'), '6' ,'0')) FROM dual)";
		String sequence_current_sql = "(SELECT CONCAT('USER', LPAD(currentval('user_sequence'), '6' ,'0')) FROM dual)";
		query.append("insert into ")
		   .append("user(user_id, user_name, nickname, email, phone_number, login_type, "
			     + "active, reg_dt, reg_id, mod_dt, mod_id) ")
		   .append("values ("+ sequence_next_sql +", ?, ?, ?, ?, ?, 1, now(), "+ sequence_current_sql +", "
			     + "now(), "+ sequence_current_sql +")");
		return jdbcTemplate.update(query.toString(), user.getUserName()
			, user.getUserNickname(), user.getUserEmail(), user.getUserPhoneNumber()
			, user.getUserLoginType());
	}
}