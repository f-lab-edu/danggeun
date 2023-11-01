package com.danggeun.wish.dto;

import com.danggeun.article.dto.ArticleRequestDto;
import com.danggeun.article.dto.ArticleResponseDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-01T19:02:11+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
public class WishResponseMapperImpl implements WishResponseMapper {

    @Override
    public WishResponseDto toWishResponseDto(WishRequestDto wishRequestDto) {
        if ( wishRequestDto == null ) {
            return null;
        }

        WishResponseDto wishResponseDto = new WishResponseDto();

        wishResponseDto.setWishId( wishRequestDto.getWishId() );
        wishResponseDto.setActive( wishRequestDto.isActive() );
        wishResponseDto.setRegisteredDate( wishRequestDto.getRegisteredDate() );
        wishResponseDto.setRegisteredId( wishRequestDto.getRegisteredId() );
        wishResponseDto.setModifiedDate( wishRequestDto.getModifiedDate() );
        wishResponseDto.setModifiedId( wishRequestDto.getModifiedId() );
        wishResponseDto.setArticleDto( articleRequestDtoToArticleResponseDto( wishRequestDto.getArticleDto() ) );

        return wishResponseDto;
    }

    protected ArticleResponseDto articleRequestDtoToArticleResponseDto(ArticleRequestDto articleRequestDto) {
        if ( articleRequestDto == null ) {
            return null;
        }

        ArticleResponseDto articleResponseDto = new ArticleResponseDto();

        articleResponseDto.setArticleId( articleRequestDto.getArticleId() );
        articleResponseDto.setUserId( articleRequestDto.getUserId() );
        articleResponseDto.setCommentId( articleRequestDto.getCommentId() );
        articleResponseDto.setRegionId( articleRequestDto.getRegionId() );
        articleResponseDto.setGroupId( articleRequestDto.getGroupId() );
        articleResponseDto.setSubject( articleRequestDto.getSubject() );
        articleResponseDto.setContext( articleRequestDto.getContext() );
        articleResponseDto.setArticleType( articleRequestDto.getArticleType() );
        articleResponseDto.setPrice( articleRequestDto.getPrice() );
        articleResponseDto.setActive( articleRequestDto.isActive() );
        articleResponseDto.setRegisteredDate( articleRequestDto.getRegisteredDate() );
        articleResponseDto.setRegisteredId( articleRequestDto.getRegisteredId() );
        articleResponseDto.setModifiedDate( articleRequestDto.getModifiedDate() );
        articleResponseDto.setModifiedId( articleRequestDto.getModifiedId() );

        return articleResponseDto;
    }
}
