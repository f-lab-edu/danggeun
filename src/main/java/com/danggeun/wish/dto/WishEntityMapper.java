package com.danggeun.wish.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.danggeun.wish.domain.Wish;

@Mapper
public interface WishEntityMapper {
	WishEntityMapper INSTANCE = Mappers.getMapper(WishEntityMapper.class);

	Wish toWishEntity(WishRequestDto wishRequestDto);
}
