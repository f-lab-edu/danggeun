package com.danggeun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DanggeunApplication {

	public static void main(String[] args) {
		SpringApplication.run(DanggeunApplication.class, args);
	}

}