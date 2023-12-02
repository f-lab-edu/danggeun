package com.danggeun.comment.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.danggeun.comment.domain.Comment;

@Mapper
public interface CommentEntityMapper {
	CommentEntityMapper INSTANCE = Mappers.getMapper(CommentEntityMapper.class);

	Comment toCommentEntity(CommentRequestDto commentRequestDto);
}
