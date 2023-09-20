package com.danggeun.article.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	 * @return ArticleDTO
	 */
	@PostMapping
	public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {

		// 신규 articleId 생성
		articleService.createArticle(articleDTO);

		// 생성된 자원 LocationURL 담을 헤더 생성
		HttpHeaders responseHeaders = new HttpHeaders();

		URI locationURI = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{articleId}")
			.buildAndExpand(articleDTO.getArticleId())
			.toUri();

		responseHeaders.setLocation(locationURI);

		return new ResponseEntity<>(articleDTO, responseHeaders, HttpStatus.CREATED);
	}

	/**
	 * 게시글 수정
	 * @param articleDTO
	 * @return ArticleDTO
	 */
	@PutMapping
	public ResponseEntity<ArticleDTO> modifyArticle(@RequestBody ArticleDTO articleDTO) {
		return new ResponseEntity<>(articleService.modifyArticle(articleDTO), HttpStatus.OK);
	}

	/**
	 * 게시글 삭제
	 * @param articleDTO
	 * @return HttpStatus.OK
	 */
	@DeleteMapping
	public ResponseEntity<ArticleDTO> deleteArticle(@RequestBody ArticleDTO articleDTO) {
		return new ResponseEntity<>(articleService.deleteArticle(articleDTO), HttpStatus.OK);
	}

	/**
	 * 게시글 상세 조회
	 * @param articleId
	 * @return ArticleDTO
	 */
	@GetMapping(value = "/{articleId}")
	public ResponseEntity<ArticleDTO> articleById(@PathVariable(value = "articleId") String articleId) {
		ArticleDTO result = articleService.findById(articleId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/**
	 * 게시글 전체 조회
	 * @return List<ArticleDTO>
	 */
	@GetMapping
	public ResponseEntity<List<ArticleDTO>> articleById() {
		List<ArticleDTO> result = articleService.findByAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
