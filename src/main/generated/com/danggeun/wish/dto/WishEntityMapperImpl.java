package com.danggeun.wish.dto;

import com.danggeun.wish.domain.Wish;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-02T21:37:05+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
public class WishEntityMapperImpl implements WishEntityMapper {

    @Override
    public Wish toWishEntity(WishRequestDto wishRequestDto) {
        if ( wishRequestDto == null ) {
            return null;
        }

        Wish wish = new Wish();

        wish.setActive( wishRequestDto.isActive() );

        return wish;
    }
}
