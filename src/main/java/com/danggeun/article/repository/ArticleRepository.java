package com.danggeun.article.repository;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.danggeun.article.dto.ArticleDTO;

@Service
public interface ArticleRepository {

	/**
	 * 게시글 생성
	 * @param articleDTO
	 * @return ArticleDTO
	 */
	public ArticleDTO createArticle(ArticleDTO articleDTO);

	/**
	 * 게시글 수정
	 * @param articleDTO
	 * @return ArticleDTO
	 */
	public ArticleDTO modifyArticle(ArticleDTO articleDTO);

	/**
	 * 게시글 삭제
	 * @param articleDTO
	 * @return ArticleDTO
	 */
	public ArticleDTO deleteArticle(ArticleDTO articleDTO);

	/**
	 * 게시글 시퀀스 생성
	 * @param prefix
	 * @param name
	 * @return String
	 */
	public String newSequence(String prefix, String name);

	/**
	 * 게시글 ID 조회
	 * @param id
	 * @return Optional<ArticleDTO>
	 */
	public Optional<ArticleDTO> findById(String id);

}
