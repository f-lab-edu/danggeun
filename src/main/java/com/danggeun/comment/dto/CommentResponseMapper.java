package com.danggeun.comment.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentResponseMapper {
	CommentResponseMapper INSTANCE = Mappers.getMapper(CommentResponseMapper.class);

	CommentResponseDto toCommentResponseDto(CommentRequestDto commentRequestDto);
}
