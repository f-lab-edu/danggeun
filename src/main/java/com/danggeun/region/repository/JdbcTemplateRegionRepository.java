package com.danggeun.region.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.danggeun.user.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JdbcTemplateRegionRepository implements RegionRepository {
	private final JdbcTemplate jdbcTemplate;

	/**
	 * 사용자 지역 정보 생성
	 * @param userDTO
	 */
	@Override
	public void save(UserDTO userDTO) {
		StringBuilder query = new StringBuilder();
		query.append("insert into ")
			.append("user_region(user_id, represent_region_status, region_range_status, ")
			.append("longitude, latitude, active, created_date, ")
			.append("created_id, modified_date, modified_id) ")
			.append("values (?, ?, ?, ?, ?, ?, now(), ?, now(), ?)");
		jdbcTemplate.update(query.toString(), userDTO.getUserId(), userDTO.getRepresentRegionStatus(),
			userDTO.getRegionRangeStatus(), userDTO.getLongitude(), userDTO.getLatitude(), userDTO.isUserActive(),
			userDTO.getCreateId(), userDTO.getModifiedId());
	}
}
