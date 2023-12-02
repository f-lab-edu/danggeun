package com.danggeun.article.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.danggeun.article.domain.Article;

@Mapper
public interface ArticleEntityMapper {
	ArticleEntityMapper INSTANCE = Mappers.getMapper(ArticleEntityMapper.class);

	// ArticleRequestDTO -> Article Entity 매핑
	Article toArticleEntity(ArticleRequestDto articleRequestDto);
}
