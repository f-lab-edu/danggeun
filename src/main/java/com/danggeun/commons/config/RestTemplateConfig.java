package com.danggeun.commons.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import com.danggeun.commons.util.RandomNumber;

import lombok.extern.slf4j.Slf4j;

@Configuration
public class RestTemplateConfig {

	/**
	 * RestTemplate은 기본적인 Timeout 제한이 없기 때문에 Timeout 설정
	 * @return RestTemplate
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder()
			.setConnectTimeout(Duration.ofSeconds(5))
			.setReadTimeout(Duration.ofSeconds(5))
			.additionalInterceptors(new LoggingInterceptor())
			.requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
			.build();
	}

	@Slf4j
	static class LoggingInterceptor implements ClientHttpRequestInterceptor {
		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {
			final String sessionNumber = sessionNumber();
			printRequest(sessionNumber, request, body);
			ClientHttpResponse response = execution.execute(request, body);
			printResponse(sessionNumber, response);
			return response;
		}

		/**
		 * RestTemplate API 호출 sessionNumber 랜덤 5자리 생성
		 * @return RandomNumber
		 */
		private String sessionNumber() {
			RandomNumber randomNumber = new RandomNumber();
			return randomNumber.getNumber();
		}

		/**
		 * RestTemplate API 호출 요청 로그
		 * @param sessionNumber, request, body
		 */
		private void printRequest(final String sessionNumber, final HttpRequest request, final byte[] body) {
			log.info("[{}] [Request] URI: {}, Method: {}, Headers: {}, Body: {}",
				sessionNumber, request.getURI(), request.getMethod(), request.getHeaders(),
				new String(body, StandardCharsets.UTF_8));
		}

		/**
		 * RestTemplate API 호출 응답 로그
		 * @param sessionNumber, request
		 * @throws IOException
		 */
		private void printResponse(final String sessionNumber, final ClientHttpResponse response) throws
			IOException {
			String body = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);

			log.info("[{}] [Response] Status: {}, Headers: {}, body: {}",
				sessionNumber, response.getStatusCode(), response.getHeaders(), body);
		}
	}
}
