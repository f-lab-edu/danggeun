package com.danggeun.comment.dto;

import com.danggeun.article.dto.ArticleRequestDto;
import com.danggeun.article.dto.ArticleResponseDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-16T20:51:05+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
public class CommentResponseMapperImpl implements CommentResponseMapper {

    @Override
    public CommentResponseDto toCommentResponseDto(CommentRequestDto commentRequestDto) {
        if ( commentRequestDto == null ) {
            return null;
        }

        CommentResponseDto commentResponseDto = new CommentResponseDto();

        commentResponseDto.setCommentId( commentRequestDto.getCommentId() );
        commentResponseDto.setContext( commentRequestDto.getContext() );
        commentResponseDto.setActive( commentRequestDto.isActive() );
        commentResponseDto.setRegisteredDate( commentRequestDto.getRegisteredDate() );
        commentResponseDto.setRegisteredId( commentRequestDto.getRegisteredId() );
        commentResponseDto.setModifiedDate( commentRequestDto.getModifiedDate() );
        commentResponseDto.setModifiedId( commentRequestDto.getModifiedId() );
        commentResponseDto.setArticleDto( articleRequestDtoToArticleResponseDto( commentRequestDto.getArticleDto() ) );

        return commentResponseDto;
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