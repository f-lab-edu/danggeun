package com.danggeun.article.dto;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-16T19:41:40+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
public class ArticleResponseMapperImpl implements ArticleResponseMapper {

    @Override
    public ArticleResponseDto toArticleResponseDto(ArticleRequestDto articleRequestDto) {
        if ( articleRequestDto == null ) {
            return null;
        }

        ArticleResponseDto articleResponseDto = new ArticleResponseDto();

        articleResponseDto.setActive( articleRequestDto.isActive() );
        articleResponseDto.setArticleId( articleRequestDto.getArticleId() );
        articleResponseDto.setUserId( articleRequestDto.getUserId() );
        articleResponseDto.setCommentId( articleRequestDto.getCommentId() );
        articleResponseDto.setRegionId( articleRequestDto.getRegionId() );
        articleResponseDto.setGroupId( articleRequestDto.getGroupId() );
        articleResponseDto.setSubject( articleRequestDto.getSubject() );
        articleResponseDto.setContext( articleRequestDto.getContext() );
        articleResponseDto.setArticleType( articleRequestDto.getArticleType() );
        articleResponseDto.setPrice( articleRequestDto.getPrice() );
        articleResponseDto.setRegisteredDate( articleRequestDto.getRegisteredDate() );
        articleResponseDto.setRegisteredId( articleRequestDto.getRegisteredId() );
        articleResponseDto.setModifiedDate( articleRequestDto.getModifiedDate() );
        articleResponseDto.setModifiedId( articleRequestDto.getModifiedId() );

        return articleResponseDto;
    }
}
