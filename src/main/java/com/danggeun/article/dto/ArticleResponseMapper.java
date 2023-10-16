package com.danggeun.article.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleResponseMapper {
	ArticleResponseMapper INSTANCE = Mappers.getMapper(ArticleResponseMapper.class);

	// ArticleRequestDTO -> ArticleResponseDTO 매핑
	ArticleResponseDto toArticleResponseDto(ArticleRequestDto articleRequestDto);
}
