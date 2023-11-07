package com.danggeun.region.repository;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.danggeun.region.dto.RegionDTO;

//@RequiredArgsConstructor
@Repository
public class JdbcTemplateRegionRepository implements RegionRepository {
	private final SimpleJdbcInsert simpleJdbcInsert;

	public JdbcTemplateRegionRepository(DataSource dataSource) {
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("user_region")
			.usingGeneratedKeyColumns("region_id")
			.usingColumns("user_id", "represent_region_status", "region_range_status", "longitude", "latitude",
				"active", "created_id", "modified_id");
	}

	/**
	 * 사용자 지역 정보 생성
	 * @param regionDTO
	 * @return regionDTO
	 */
	@Override
	public RegionDTO save(RegionDTO regionDTO) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("user_id", regionDTO.getUserId())
			.addValue("represent_region_status", regionDTO.getRepresentRegionStatus())
			.addValue("region_range_status", regionDTO.getRegionRangeStatus())
			.addValue("longitude", regionDTO.getLongitude())
			.addValue("latitude", regionDTO.getLatitude())
			.addValue("active", regionDTO.isUserActive())
			.addValue("created_id", regionDTO.getCreatedId())
			.addValue("modified_id", regionDTO.getModifiedId());
		Number number = simpleJdbcInsert.executeAndReturnKey(param);

		regionDTO.setRegionId((Long)number);
		return regionDTO;
	}
}
