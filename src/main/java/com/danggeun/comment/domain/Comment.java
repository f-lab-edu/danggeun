package com.danggeun.comment.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Comment {

	private Long id;
	private String context;
	private boolean active;
	private LocalDateTime registeredDate;
	private String registeredId;
	private LocalDateTime modifiedDate;
	private String modifiedId;

}
