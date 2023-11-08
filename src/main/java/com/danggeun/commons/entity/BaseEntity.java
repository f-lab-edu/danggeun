package com.danggeun.commons.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public class BaseEntity extends BaseTimeEntity {

	@Column(updatable = false)
	private String registeredId;

	private String modifiedId;

}
