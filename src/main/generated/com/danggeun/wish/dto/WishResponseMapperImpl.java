package com.danggeun.wish.dto;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-03T17:06:48+0900",
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
        wishResponseDto.setUserId( wishRequestDto.getUserId() );
        wishResponseDto.setArticleId( wishRequestDto.getArticleId() );
        wishResponseDto.setRegisteredDate( wishRequestDto.getRegisteredDate() );
        wishResponseDto.setModifiedDate( wishRequestDto.getModifiedDate() );

        return wishResponseDto;
    }
}
