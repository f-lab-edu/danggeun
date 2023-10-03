package com.danggeun.article.repository;

import java.util.List;
import java.util.Optional;

import com.danggeun.article.dto.ArticleDTO;

public interface ArticleRepository {

	/**
	 * 게시글 생성
	 * @param articleDTO
	 * @return Article
	 */
	public ArticleDTO createArticle(ArticleDTO articleDTO);

	/**
	 * 게시글 수정
	 * @param articleDTO
	 * @return int
	 */
	public int modifyArticle(ArticleDTO articleDTO);

	/**
	 * 게시글 삭제
	 * @param articleDTO
	 * @return int
	 */
	public int deleteArticle(ArticleDTO articleDTO);

	/**
	 * 게시글 ID 조회
	 * @param id
	 * @return Optional<Article>
	 */
	public Optional<ArticleDTO> findById(String id);

	/**
	 * 게시글 전체 조회
	 * @return List<Article>
	 */
	public List<ArticleDTO> findByAll();

}
