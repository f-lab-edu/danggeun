package com.danggeun.comment.dto;

import com.danggeun.comment.domain.Comment;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-28T18:58:29+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
public class CommentEntityMapperImpl implements CommentEntityMapper {

    @Override
    public Comment toCommentEntity(CommentRequestDto commentRequestDto) {
        if ( commentRequestDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setCommentId( commentRequestDto.getCommentId() );
        comment.setContext( commentRequestDto.getContext() );
        comment.setActive( commentRequestDto.isActive() );

        return comment;
    }
}
