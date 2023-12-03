package com.danggeun.wish.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danggeun.wish.dto.WishRequestDto;
import com.danggeun.wish.dto.WishResponseDto;
import com.danggeun.wish.service.WishService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(value = "/api/wishes")
@RestController
@RequiredArgsConstructor
public class WishController {

	private final WishService wishService;

	// 관심목록 등록
	@PostMapping
	public ResponseEntity<WishResponseDto> createWish(@RequestBody WishRequestDto wishRequestDto) {
		return new ResponseEntity<>(wishService.createWish(wishRequestDto), HttpStatus.OK);
	}

	// 관심목록 수정
	@PutMapping("/{wishId}")
	public ResponseEntity modifyWish(@PathVariable("wishId") Integer wishId) {
		wishService.modifyWish(wishId);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 관심목록 삭제
	@DeleteMapping("/{wishId}")
	public ResponseEntity deleteWish(@PathVariable("wishId") Integer wishId) {
		wishService.deleteWish(wishId);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 관심목록 조회
	@GetMapping("/{userId}")
	public ResponseEntity<Page<WishResponseDto>> findByUserWish(@PathVariable(value = "userId") Integer userId,
		@PageableDefault(size = 3, sort = "wishId", direction = Sort.Direction.DESC) Pageable pageable) {
		return new ResponseEntity<>(wishService.findByUserId(pageable, userId), HttpStatus.OK);
	}
}
