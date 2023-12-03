package com.danggeun.wish.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danggeun.wish.domain.Wish;
import com.danggeun.wish.dto.WishEntityMapperImpl;
import com.danggeun.wish.dto.WishRequestDto;
import com.danggeun.wish.dto.WishResponseDto;
import com.danggeun.wish.repository.WishRepository;
import com.danggeun.wish.repository.jpa.WishJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishService {

	private final WishRepository wishRepository;
	private final WishJpaRepository wishJpaRepository;
	private final WishEntityMapperImpl wishEntityMapper = new WishEntityMapperImpl();

	@PersistenceContext
	private final EntityManager em;

	@Transactional
	public WishResponseDto createWish(WishRequestDto wishRequestDto) {
		Wish wish = wishEntityMapper.toWishEntity(wishRequestDto);
		if (wishRequestDto.hasId()) {
			wish.setActive(true);
		} else {
			wishJpaRepository.save(wish);
		}
		return new WishResponseDto(wish);
	}

	@Transactional
	public void modifyWish(Integer wishId) {
		Optional<Wish> find = wishJpaRepository.findById(wishId);
		if (find.isPresent()) {
			Wish wish = find.get();
			wish.setActive(!wish.isActive());
		}
	}

	@Transactional
	public void deleteWish(Integer wishId) {
		Optional<Wish> find = wishJpaRepository.findById(wishId);
		if (find.isPresent()) {
			Wish wish = find.get();
			wish.setActive(false);
		}
	}

	public List<WishResponseDto> findByAll(Pageable pageable) {
		return wishRepository.findByAll(pageable);
	}

	public List<WishResponseDto> findByUserWish(Pageable pageable, Long userId) {
		return wishRepository.findByUserId(pageable, userId);
	}

}
