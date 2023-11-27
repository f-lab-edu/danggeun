package com.danggeun.article.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danggeun.article.domain.Article;
import com.danggeun.article.dto.ArticleRequestDto;
import com.danggeun.article.dto.ArticleResponseDto;
import com.danggeun.article.exception.ArticleNotFoundException;
import com.danggeun.article.repository.ArticleRepository;
import com.danggeun.article.repository.jpa.ArticleJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

	private final ArticleRepository articleRepository;
	private final ArticleJpaRepository articleJpaRepository;

	/**
	 * 게시글 생성
	 * @param articleRequestDto
	 * @return ArticleResponseDto
	 */
	@Transactional
	public ArticleResponseDto createArticle(ArticleRequestDto articleRequestDto) {
		// Request DTO -> Entity
		Article article = articleJpaRepository.save(articleRequestDto.toEntity());

		// Entity -> Response DTO
		Optional<Article> findArticle = articleJpaRepository.findById(article.getArticleId());
		return new ArticleResponseDto(findArticle.get());
	}

	/**
	 * 게시글 수정
	 * @param articleRequestDto
	 * @return ArticleResponseDto
	 */
	@Transactional
	public ArticleResponseDto modifyArticle(ArticleRequestDto articleRequestDto) {
		Optional<Article> find = articleJpaRepository.findById(articleRequestDto.getArticleId());
		Article findArticle = find.orElseThrow(ArticleNotFoundException::new);

		findArticle.setSubject(articleRequestDto.getSubject());
		findArticle.setContext(articleRequestDto.getContext());
		findArticle.setArticleType(articleRequestDto.getArticleType());
		findArticle.setPrice(articleRequestDto.getPrice());

		Optional<Article> result = articleJpaRepository.findById(findArticle.getArticleId());
		return new ArticleResponseDto(result.get());
	}

	/**
	 * 게시글 삭제
	 * @param articleId
	 */
	@Transactional
	public void deleteArticle(Integer articleId) {
		Optional<Article> find = articleJpaRepository.findById(articleId);
		Article article = find.orElseThrow(ArticleNotFoundException::new);
		articleJpaRepository.delete(article);
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
		articleJpaRepository.findAll(pageable);
		return articleRepository.findByAll(pageable);
	}

}
