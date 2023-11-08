package com.danggeun.commons.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public class BaseTimeEntity {

	@Column(updatable = false)
	private LocalDateTime registeredDate;

	private LocalDateTime modifiedDate;

}
