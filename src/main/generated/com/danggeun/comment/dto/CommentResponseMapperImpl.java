package com.danggeun.comment.dto;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-28T18:58:29+0900",
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
        commentResponseDto.setArticleId( commentRequestDto.getArticleId() );

        return commentResponseDto;
    }
}
