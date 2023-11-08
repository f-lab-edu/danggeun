package com.danggeun.wish.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.danggeun.wish.dto.WishRequestDto;
import com.danggeun.wish.dto.WishResponseDto;
import com.danggeun.wish.repository.WishRespository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishService {

	private final WishRespository wishRespository;

	public WishResponseDto createWish(WishRequestDto wishRequestDto) {
		WishResponseDto result = null;
		if (wishRequestDto.hasId()) {
			result = wishRespository.findById(wishRequestDto.getWishId());
			// 기등록 된 케이스의 경우 active 만 살리기
			wishRespository.modifyWish(wishRequestDto);
		} else {
			result = wishRespository.createWish(wishRequestDto);
		}
		return result;
	}

	public void deleteWish(Long wishId) {
		wishRespository.deleteWish(wishId);
	}

	public List<WishResponseDto> findByAll(Pageable pageable) {
		return wishRespository.findByAll(pageable);
	}

	public List<WishResponseDto> findByUserWish(Pageable pageable, Long userId) {
		return wishRespository.findByUserId(pageable, userId);
	}

}
