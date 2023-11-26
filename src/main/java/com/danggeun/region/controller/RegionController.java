package com.danggeun.region.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danggeun.region.dto.LocationAdressResponse;
import com.danggeun.region.service.RegionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/location")
public class RegionController {

	private final RegionService locationService;

	/**
	 * 지역 정보 검색
	 * @param address
	 * @return region info
	 */
	@GetMapping(value = "/{address}")
	public ResponseEntity<LocationAdressResponse> locationByAddress(@PathVariable String address) throws IOException {
		return new ResponseEntity<>(locationService.findByAddress(address), HttpStatus.OK);
	}

}
