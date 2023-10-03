package com.danggeun.article.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danggeun.article.dto.ArticleDTO;
import com.danggeun.article.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

	private final ArticleRepository articleRepository;

	/**
	 * 게시글 생성
	 * @param articleDTO
	 * @return ArticleDTO
	 */
	@Transactional
	public ArticleDTO createArticle(ArticleDTO articleDTO) {

		// 게시글 타입별 필수 값 체크
		articleDTO.validateArticleNullable();

		return articleRepository.createArticle(articleDTO);
	}

	/**
	 * 게시글 수정
	 * @param articleDTO
	 */
	public void modifyArticle(ArticleDTO articleDTO) {
		// 게시글 ID 존재 여부 확인
		articleDTO.hasId();

		// 게시글 타입별 필수 값 체크
		articleDTO.validateArticleNullable();

		articleRepository.modifyArticle(articleDTO);
	}

	/**
	 * 게시글 삭제
	 * @param articleDTO
	 */
	public void deleteArticle(ArticleDTO articleDTO) {
		// 게시글 ID 존재 여부 확인
		articleDTO.hasId();

		articleRepository.deleteArticle(articleDTO);
	}

	/**
	 * 게시글 상세 조회
	 * @param articleId
	 * @return ArticleDTO
	 */
	public ArticleDTO findById(String articleId) {
		Optional<ArticleDTO> result = articleRepository.findById(articleId);
		if (result.isPresent()) {
			return result.get();
		} else {
			throw new IllegalStateException("존재하지 않는 게시물 입니다.");
		}
	}

	/**
	 * 게시글 전체 조회
	 * @return List<Article>
	 */
	public List<ArticleDTO> findByAll() {
		return articleRepository.findByAll();
	}

}
