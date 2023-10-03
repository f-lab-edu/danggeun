package com.danggeun.article.controller;

import java.util.List;

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

import com.danggeun.article.dto.ArticleDTO;
import com.danggeun.article.service.ArticleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(value = "/api/articles")
@RestController
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

	/**
	 * 게시글 생성
	 * @param articleDTO
	 * @return ResponseEntity
	 */
	@PostMapping
	public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {
		return new ResponseEntity<>(articleService.createArticle(articleDTO), HttpStatus.CREATED);
	}

	/**
	 * 게시글 수정
	 * @param articleDTO
	 * @return ResponseEntity
	 */
	@PutMapping
	public ResponseEntity modifyArticle(@RequestBody ArticleDTO articleDTO) {
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 게시글 삭제
	 * @param articleDTO
	 * @return ResponseEntity
	 */
	@DeleteMapping
	public ResponseEntity deleteArticle(@RequestBody ArticleDTO articleDTO) {
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 게시글 상세 조회
	 * @param articleId
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/{articleId}")
	public ResponseEntity<ArticleDTO> articleById(@PathVariable(value = "articleId") String articleId) {
		ArticleDTO result = articleService.findById(articleId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/**
	 * 게시글 전체 조회
	 * @return ResponseEntity
	 */
	@GetMapping
	public ResponseEntity<List<ArticleDTO>> articleById() {
		List<ArticleDTO> result = articleService.findByAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
