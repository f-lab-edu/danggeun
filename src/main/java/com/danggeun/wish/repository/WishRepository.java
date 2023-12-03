package com.danggeun.wish.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.danggeun.wish.dto.WishRequestDto;
import com.danggeun.wish.dto.WishResponseDto;

public interface WishRepository {

	public WishResponseDto createWish(WishRequestDto wishRequestDto);

	public int modifyWish(Long wishId);

	public int deleteWish(Long wishId);

	public WishResponseDto findById(Long wishId);

	public List<WishResponseDto> findByAll(Pageable pageable);

	public List<WishResponseDto> findByUserId(Pageable pageable, Long userId);
}