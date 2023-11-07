package com.danggeun.region.service;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.danggeun.region.dto.LocationAdressResponse;
import com.danggeun.region.exception.AddressSearchKakaoApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RegionService {

	private static final String REST_API_KEY = "1cf9df42761b5c939fd478061f6e91ef";
	private static final String KAKAO_SERVICE_URL = "https://dapi.kakao.com";

	private final RestTemplate restTemplate;

	/**
	 * kakao api 주소 검색
	 * @param address
	 * @return address info
	 */
	public LocationAdressResponse findByAddress(String address) throws IOException {
		String uri = UriComponentsBuilder.fromHttpUrl(KAKAO_SERVICE_URL)
			.path("/v2/local/search/address.json")
			.queryParam("query", address)
			.build()
			.toUriString();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "KakaoAK " + REST_API_KEY);
		HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

		ResponseEntity<LocationAdressResponse> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity,
			LocationAdressResponse.class);

		if (response.getStatusCode().is2xxSuccessful())
			return response.getBody();
		else
			throw new AddressSearchKakaoApiException(response.getStatusCode().toString());
	}
}
