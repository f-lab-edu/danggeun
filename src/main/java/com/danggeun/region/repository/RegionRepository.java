package com.danggeun.region.repository;

import com.danggeun.region.dto.RegionDTO;

public interface RegionRepository {

	/**
	 * 사용자 지역 정보 생성
	 * @param regionDTO
	 */
	public RegionDTO save(RegionDTO regionDTO);
}
