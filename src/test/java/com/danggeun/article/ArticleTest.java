package com.danggeun.article;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.danggeun.annotation.SpringIntegrationTest;
import com.danggeun.article.controller.ArticleController;
import com.danggeun.article.dto.ArticleRequestDto;
import com.danggeun.article.dto.ArticleResponseDto;
import com.danggeun.article.enumerate.ArticleType;
import com.danggeun.article.exception.ArticleNotFoundException;
import com.danggeun.article.repository.jdbctemplate.JdbcTemplateArticleRepository;
import com.danggeun.article.service.ArticleService;

@SpringIntegrationTest
class ArticleTest {

	@Autowired
	ArticleController articleController;

	@Autowired
	ArticleService articleService;

	@Autowired
	JdbcTemplateArticleRepository articleRepository;

	ArticleRequestDto articleRequestDTO = null;

	@BeforeEach
	void setUp() {
		articleRequestDTO = new ArticleRequestDto(9999, 9999, null, 9999, null, "test subject", "test context",
			ArticleType.NORMAL, null, true, null, "testDevelop", null, "testDevelop");
	}

	/**
	 * 게시글 신규 등록
	 * 1. 신규 생성 정상 작동 시 생성 되는 ArticleId 가 있어야 한다.
	 * 2. 신규 생성 시 필수 값이 존재 하지 않으면 Exception 발생 한다.
	 */
	@Test
	@DisplayName("게시글 신규 등록")
	void articleCreate() {
		ArticleResponseDto resultDTO = articleService.createArticle(articleRequestDTO);
		// 신규 생성 시 ArticleId가 넘어 와야 한다.
		assertThat(resultDTO.getArticleId()).isNotNull();

		// 필수 값 없는 경우 Exception 발생
		articleRequestDTO.setSubject(null);
		assertThrows(IllegalArgumentException.class, () -> articleController.createArticle(articleRequestDTO));
	}

	/**
	 * 게시글 수정, 삭제 시 게시글 ID 미 존재 시 Exception 발생
	 */
	@Test
	@DisplayName("게시글 수정, 삭제 시 게시글 ID 미 존재 시 Exception 발생")
	void articleModifyIllegalArgumentArticleId() {
		articleRequestDTO.setArticleId(null);
		assertThrows(IllegalArgumentException.class, () -> {
			articleController.modifyArticle(articleRequestDTO);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			articleController.deleteArticle(articleRequestDTO);
		});

	}

	/**
	 * 게시글 타입별 필수값 체크
	 */
	@Test
	@DisplayName("게시글 타입에 따른 필수값 체크 여부 확인")
	void articleNullable() {
		// 일상 게시글 ArticleType.NORMAl
		articleRequestDTO.setArticleType(ArticleType.NORMAL);
		articleRequestDTO.setContext(null);
		assertThrows(IllegalArgumentException.class, () -> {
			articleRequestDTO.validateArticleNullable();
		});

		articleRequestDTO.setContext("test context");
		// 커뮤니티 모집 게시글 ArticleType.GROUP
		articleRequestDTO.setArticleType(ArticleType.GROUP);
		articleRequestDTO.setGroupId(null);
		assertThrows(IllegalArgumentException.class, () -> {
			articleRequestDTO.validateArticleNullable();
		});

		// 중고거래 게시글 ArticleType.TRADE
		articleRequestDTO.setArticleType(ArticleType.TRADE);
		articleRequestDTO.setPrice(null);
		assertThrows(IllegalArgumentException.class, () -> {
			articleRequestDTO.validateArticleNullable();
		});

	}

	/**
	 * 게시글 조회
	 */
	@Test
	@DisplayName("게시글 ID 조회 및 전체 리스트 조회")
	void articleSearch() {
		// 9999 ID 저장
		articleService.createArticle(articleRequestDTO);

		// articleId 로 조회
		int articleId = 1;
		ArticleResponseDto findArticle = articleService.findById(articleId);
		assertThat(findArticle.getArticleId()).isEqualTo(articleId);

		// 페이징 처리 객체 생성
		Pageable pageable = PageRequest.of(0, 3);

		List<ArticleResponseDto> articles = articleService.findByAll(pageable);
		assertThat(articles).isNotNull();
		assertThat(articles.size()).isEqualTo(3);
	}

	@Test
	@DisplayName("게시글 조회 시 존재 하지 않는 경우 예외 발생")
	void articleNotFound() {
		assertThrows(ArticleNotFoundException.class, () -> {
			articleService.findById(99999);
		});
	}

}