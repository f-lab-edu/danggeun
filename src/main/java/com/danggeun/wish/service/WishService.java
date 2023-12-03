package com.danggeun.wish.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danggeun.article.domain.Article;
import com.danggeun.article.exception.ArticleNotFoundException;
import com.danggeun.article.repository.jpa.ArticleJpaRepository;
import com.danggeun.wish.domain.Wish;
import com.danggeun.wish.dto.WishEntityMapperImpl;
import com.danggeun.wish.dto.WishRequestDto;
import com.danggeun.wish.dto.WishResponseDto;
import com.danggeun.wish.repository.WishRepository;
import com.danggeun.wish.repository.jpa.WishJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishService {

	private final WishRepository wishRepository;
	private final WishJpaRepository wishJpaRepository;
	private final ArticleJpaRepository articleJpaRepository;
	private final WishEntityMapperImpl wishEntityMapper = new WishEntityMapperImpl();

	@Transactional
	public WishResponseDto createWish(WishRequestDto wishRequestDto) {
		Wish wish = wishEntityMapper.toWishEntity(wishRequestDto);

		// 게시글 정보 확인
		Integer articleId = wishRequestDto.getArticleId();
		Optional<Article> findArticle = articleJpaRepository.findById(articleId);
		Article article = findArticle.orElseThrow(ArticleNotFoundException::new);
		wish.setArticle(article);

		// 중복 검사

		// 관심목록 등록
		Wish save = wishJpaRepository.save(wish);
		Optional<Wish> find = wishJpaRepository.findById(save.getWishId());
		Wish findWish = find.orElseThrow(IllegalStateException::new);
		return new WishResponseDto(findWish);
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

	@Transactional(readOnly = true)
	public Page<WishResponseDto> findByAll(Pageable pageable) {
		return wishJpaRepository.findAllDto(pageable);
	}

	@Transactional(readOnly = true)
	public Page<WishResponseDto> findByUserId(Pageable pageable, Integer userId) {
		return wishJpaRepository.findByUserId(pageable, userId);
	}

}
