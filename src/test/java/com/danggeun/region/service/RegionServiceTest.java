package com.danggeun.region.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.danggeun.region.dto.LocationAdressResponse;

@SpringBootTest
class RegionServiceTest {

	/**
	 * KAKAO API 주소 검색
	 */
	@Test
	@DisplayName("카카오 주소 검색 API")
	void findByAddress() {
		//given
		String query = "숭인동";
		String REST_API_KEY = "1cf9df42761b5c939fd478061f6e91ef";
		String KAKAO_SERVICE_URL = "https://dapi.kakao.com/v2/local/search/address.json?query=" + query;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "KakaoAK " + REST_API_KEY);
		HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

		//when
		ResponseEntity<LocationAdressResponse> response = restTemplate.exchange(KAKAO_SERVICE_URL, HttpMethod.GET,
			httpEntity,
			LocationAdressResponse.class);

		//then
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

	}

}