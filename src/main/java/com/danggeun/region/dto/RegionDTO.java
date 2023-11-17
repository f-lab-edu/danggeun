package com.danggeun.region.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RegionDTO {
	private Long regionId;
	private Long userId;
	private Double longitude; // x
	private Double latitude; // y
	private String representRegionStatus; // 대표 지역
	private String regionRangeStatus; // 지역 범위
	private boolean userActive;
	private String createdId;
	private Date createdDate;
	private String modifiedId;
	private Date modifiedDate;
}
