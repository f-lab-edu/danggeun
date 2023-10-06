package com.danggeun.article.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.danggeun.article.dto.ArticleRequestDto;
import com.danggeun.article.dto.ArticleResponseDto;

public interface ArticleRepository {

	/**
	 * 게시글 생성
	 * @param articleRequestDto
	 * @return ArticleResponseDto
	 */
	public ArticleResponseDto createArticle(ArticleRequestDto articleRequestDto);

	/**
	 * 게시글 수정
	 * @param articleRequestDto
	 * @return ArticleResponseDto
	 */
	public ArticleResponseDto modifyArticle(ArticleRequestDto articleRequestDto);

	/**
	 * 게시글 삭제
	 * @param articleRequestDto
	 * @return int
	 */
	public int deleteArticle(ArticleRequestDto articleRequestDto);

	/**
	 * 게시글 ID 조회
	 * @param articleId
	 * @return Optional<ArticleResponseDto>
	 */
	public Optional<ArticleResponseDto> findById(int articleId);

	/**
	 * 게시글 전체 조회
	 * @param pageable
	 * @return List<ArticleResponseDto>
	 */
	public List<ArticleResponseDto> findByAll(Pageable pageable);

}
