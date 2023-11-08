package com.danggeun.region.dto;

import lombok.Data;

@Data
public class Address {
	private String addressName;
	private String region_1depth_name;
	private String region_2depth_name;
	private String region_3depth_name;
	private String region_3depth_h_name;
	private String b_code;
	private String h_code;
	private String main_address_no;
	private String mountain_yn;
	private String sub_address_no;
	private String x;
	private String y;
}
