package com.danggeun.region.repository;

import com.danggeun.user.dto.UserDTO;

public interface RegionRepository {

	/**
	 * 사용자 지역 정보 생성
	 * @param userDTO
	 */
	public void save(UserDTO userDTO);
}
