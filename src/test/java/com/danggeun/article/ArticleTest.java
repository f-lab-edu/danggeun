package com.danggeun.article;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.danggeun.article.controller.ArticleController;
import com.danggeun.article.dto.ArticleDTO;
import com.danggeun.article.enumerate.ArticleType;
import com.danggeun.article.repository.jdbctemplate.JdbcTemplateArticleRepository;
import com.danggeun.article.service.ArticleService;

@ActiveProfiles("local")
@SpringBootTest
@Transactional
class ArticleTest {

	@Autowired
	ArticleController articleController;

	@Autowired
	ArticleService articleService;

	@Autowired
	JdbcTemplateArticleRepository articleRepository;

	ArticleDTO articleDTO = null;

	@BeforeEach
	void setUp() {
		articleDTO = new ArticleDTO();
		articleDTO.setArticleId(9999);
		articleDTO.setUserId(9999);
		articleDTO.setRegionId(9999);
		articleDTO.setSubject("test subject");
		articleDTO.setContext("test context");
		articleDTO.setArticleType(ArticleType.NORMAL);
		articleDTO.setActive(true);
		articleDTO.setRegisteredId("testDevelop");
		articleDTO.setModifiedId("testDevelop");
	}

	/**
	 * 게시글 신규 등록
	 * 1. 신규 생성 정상 작동 시 생성 되는 ArticleId 가 있어야 한다.
	 * 2. 신규 생성 시 필수 값이 존재 하지 않으면 Exception 발생 한다.
	 */
	@Test
	@DisplayName("게시글 신규 등록")
	void articleCreate() {
		ArticleDTO resultDTO = articleService.createArticle(articleDTO);
		// 신규 생성 시 ArticleId가 넘어 와야 한다.
		assertThat(resultDTO.getArticleId()).isNotNull();

		resultDTO.setSubject(null);
		assertThrows(IllegalArgumentException.class, () -> articleService.createArticle(resultDTO));
	}

	/**
	 * 게시글 수정, 삭제 시 게시글 ID 미 존재 시 오류 발생
	 */
	@Test
	@DisplayName("게시글 수정, 삭제 시 게시글 ID 미 존재 시 Exception 발생")
	void articleModifyIllegalArgumentArticleId() {
		articleDTO.setArticleId(null);
		assertThrows(IllegalArgumentException.class, () -> {
			articleDTO.hasId();
		});
		assertThrows(IllegalArgumentException.class, () -> {
			articleController.modifyArticle(articleDTO);
		});

	}

	/**
	 * 게시글 타입별 필수값 체크
	 */
	@Test
	@DisplayName("게시글 수정, 삭제 시 게시글 ID 미 존재 시 IllegalArgumentException 발생")
	void articleNullable() {
		articleDTO.setUserId(null);
		assertThrows(IllegalArgumentException.class, () -> {
			articleDTO.validateArticleNullable();
		});

	}

	/**
	 * 게시글 조회 시 없는 게시물의 경우 오류 발생
	 */
	@Test
	@DisplayName("존재하지 않는 게시물ID 로 게시물 조회 시 오류 발생")
	void articleNotExist() {
		String articleId = "ARTICLEnonono";
		assertThrows(IllegalStateException.class, () -> {
			articleController.articleById(articleId);
		});
	}

}