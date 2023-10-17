package com.danggeun.article.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.danggeun.article.dto.ArticleRequestDto;
import com.danggeun.article.dto.ArticleResponseDto;
import com.danggeun.article.exception.ArticleNotFoundException;
import com.danggeun.article.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

	private final ArticleRepository articleRepository;

	/**
	 * 게시글 생성
	 * @param articleRequestDto
	 * @return ArticleResponseDto
	 */
	public ArticleResponseDto createArticle(ArticleRequestDto articleRequestDto) {
		return articleRepository.createArticle(articleRequestDto);
	}

	/**
	 * 게시글 수정
	 * @param articleRequestDto
	 * @return ArticleResponseDto
	 */
	public ArticleResponseDto modifyArticle(ArticleRequestDto articleRequestDto) {
		return articleRepository.modifyArticle(articleRequestDto);
	}

	/**
	 * 게시글 삭제
	 * @param articleRequestDto
	 */
	public void deleteArticle(ArticleRequestDto articleRequestDto) {
		articleRepository.deleteArticle(articleRequestDto);
	}

	/**
	 * 게시글 상세 조회
	 * @param articleId
	 * @return ArticleResponseDto
	 */
	public ArticleResponseDto findById(int articleId) {
		Optional<ArticleResponseDto> result = articleRepository.findById(articleId);
		return result.orElseThrow(() -> new ArticleNotFoundException("존재 하지 않는 게시물 입니다."));
	}

	/**
	 * 게시글 전체 조회
	 * @param pageable
	 * @return List<ArticleResponseDto>
	 */
	public List<ArticleResponseDto> findByAll(Pageable pageable) {
		return articleRepository.findByAll(pageable);
	}

}
