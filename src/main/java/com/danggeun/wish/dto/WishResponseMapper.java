package com.danggeun.wish.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WishResponseMapper {
	WishResponseMapper INSTANCE = Mappers.getMapper(WishResponseMapper.class);

	WishResponseDto toWishResponseDto(WishRequestDto wishRequestDto);
}
