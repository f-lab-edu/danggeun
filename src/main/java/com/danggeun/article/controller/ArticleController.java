package com.danggeun.article.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import com.danggeun.article.dto.ArticleRequestDto;
import com.danggeun.article.dto.ArticleResponseDto;
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
	 * @param articleRequestDto
	 * @return ResponseEntity<ArticleResponseDto>
	 */
	@PostMapping
	public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleRequestDto articleRequestDto) {
		// 게시글 타입별 필수 값 체크
		articleRequestDto.validateArticleNullable();
		return new ResponseEntity<>(articleService.createArticle(articleRequestDto), HttpStatus.CREATED);
	}

	/**
	 * 게시글 수정
	 * @param articleRequestDto
	 * @return ResponseEntity<ArticleResponseDto>
	 */
	@PutMapping
	public ResponseEntity<ArticleResponseDto> modifyArticle(@RequestBody ArticleRequestDto articleRequestDto) {
		// 게시글 ID 존재 여부 확인
		articleRequestDto.hasId();
		// 게시글 타입별 필수 값 체크
		articleRequestDto.validateArticleNullable();

		return new ResponseEntity<>(articleService.modifyArticle(articleRequestDto), HttpStatus.OK);
	}

	/**
	 * 게시글 삭제
	 * @param articleRequestDto
	 * @return ResponseEntity
	 */
	@DeleteMapping
	public ResponseEntity<ArticleResponseDto> deleteArticle(@RequestBody ArticleRequestDto articleRequestDto) {
		// 게시글 ID 존재 여부 확인
		articleRequestDto.hasId();
		articleService.deleteArticle(articleRequestDto);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 게시글 상세 조회
	 * @param articleId
	 * @return ResponseEntity<ArticleResponseDto>
	 */
	@GetMapping(value = "/{articleId}")
	public ResponseEntity<ArticleResponseDto> findById(@PathVariable(value = "articleId") int articleId) {
		return new ResponseEntity<>(articleService.findById(articleId), HttpStatus.OK);
	}

	/**
	 * 게시글 전체 조회
	 * @param pageable
	 * @return ResponseEntity<List < ArticleResponseDto>>
	 */
	@GetMapping
	public ResponseEntity<List<ArticleResponseDto>> findByAll(
		@PageableDefault(size = 3, sort = "article_id", direction = Sort.Direction.DESC) Pageable pageable) {
		return new ResponseEntity<>(articleService.findByAll(pageable), HttpStatus.OK);
	}

}
