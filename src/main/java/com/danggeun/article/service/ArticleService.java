package com.danggeun.article.service;

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
		// 신규 게시글 시퀀스 생성
		String articleId = newSequence("ARTICLE", "article_sequence");

		// 게시글 중복 확인
		validateDuplicateArticle(articleId);

		articleDTO.setArticleId(articleId);

		// 게시글 타입별 필수 값 체크
		articleDTO.validateArticleNullable(articleDTO);

		// DTO -> ENTITY 변환 시작

		// DTO -> ENTITY 변환 종료

		return articleRepository.createArticle(articleDTO);
	}

	/**
	 * 게시글 수정
	 * @param articleDTO
	 * @return ArticleDTO
	 */
	public ArticleDTO modifyArticle(ArticleDTO articleDTO) {
		// 게시글 ID 존재 여부 확인 없을 시 IllegalArgumentException 발생
		articleDTO.hasId();

		// 게시글 타입별 필수 값 체크
		articleDTO.validateArticleNullable(articleDTO);

		return articleRepository.modifyArticle(articleDTO);
	}

	/**
	 * 게시글 삭제
	 * @param articleDTO
	 * @return ArticleDTO
	 */
	public ArticleDTO deleteArticle(ArticleDTO articleDTO) {
		// 게시글 ID 존재 여부 확인 없을 시 IllegalArgumentException 발생
		articleDTO.hasId();
		return articleRepository.deleteArticle(articleDTO);
	}

	/**
	 * 게시글 시퀀스 생성
	 * @param prefix
	 * @param name
	 * @return String
	 */
	private String newSequence(String prefix, String name) {
		return articleRepository.newSequence(prefix, name);
	}

	/**
	 * 게시글 중복 확인
	 * @param id
	 */
	public void validateDuplicateArticle(String id) {
		articleRepository.findById(id)
			.ifPresent(m -> {
				throw new IllegalStateException("이미 존재하는 게시글 입니다.");
			});
	}

}
