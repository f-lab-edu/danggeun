package com.danggeun.article;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.danggeun.article.controller.ArticleController;
import com.danggeun.article.dto.ArticleDTO;
import com.danggeun.article.enumerate.ArticleType;
import com.danggeun.article.repository.jdbctemplate.JdbcTemplateArticleRepository;
import com.danggeun.article.service.ArticleService;

@ActiveProfiles("local")
@SpringBootTest
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
		articleDTO.setArticleId("ARTICLE00001");
		articleDTO.setUserId("testDevelop");
		articleDTO.setRegionId("regionId");
		articleDTO.setSubject("test subject");
		articleDTO.setContext("test context");
		articleDTO.setArticleType(ArticleType.NOMAL);
		articleDTO.setActive(true);
		articleDTO.setRegisteredId("testDevelop");
		articleDTO.setModifiedId("testDevelop");
	}

	/**
	 * 게시글 신규 등록
	 */
	@Test
	@DisplayName("게시글 신규 등록")
	void articleCreate() {
		articleDTO.setArticleId(null);
		ArticleDTO resultDTO = articleService.createArticle(articleDTO);

		// 신규 생성 시 ArticleId가 넘어와야 한다.
		assertThat(resultDTO.getArticleId()).isNotNull();

	}

	/**
	 * 게시글 신규 등록 시 중복 ID 확인 시 오류 발생
	 */
	@Test
	@DisplayName("게시글 생성 시 게시글 ID 중복 시 오류 발생")
	void articleAddArticleIdDuplicate() {
		articleDTO.setArticleId(null);
		ArticleDTO resultDTO = articleService.createArticle(articleDTO);
		assertThrows(IllegalStateException.class, () -> {
			articleService.validateDuplicateArticle(resultDTO.getArticleId());
		});
	}

	/**
	 * 게시글 수정, 삭제 시 게시글 ID 미 존재 시 오류 발생
	 */
	@Test
	@DisplayName("게시글 수정, 삭제 시 게시글 ID 미 존재 시 IllegalArgumentException 발생")
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
			articleDTO.validateArticleNullable(articleDTO);
		});

	}
}