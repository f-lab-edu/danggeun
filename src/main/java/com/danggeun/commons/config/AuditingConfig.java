package com.danggeun.commons.config;

import java.util.Optional;
import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

@Component
@EnableJpaAuditing
public class AuditingConfig {

	@Bean
	public AuditorAware<Long> auditorProvider() {
		// 추후 세션 사용자 정보의 값으로 변경 할 수 있도록 제공
		// 현재는 Seed 값을 동일하게 주어 동일한 난수 패턴이 발생 하도록 하여 테스트
		Random random = new Random(5);
		return () -> Optional.of(random.nextLong());
	}

}
