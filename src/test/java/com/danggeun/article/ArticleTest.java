package com.danggeun.article;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.danggeun.annotation.SpringIntegrationTest;
import com.danggeun.article.domain.Article;
import com.danggeun.article.dto.ArticleDTO;
import com.danggeun.article.enumerate.ArticleType;
import com.danggeun.article.repository.jdbctemplate.JdbcTemplateArticleRepository;
import com.danggeun.article.service.ArticleService;

// @ActiveProfiles("local")
// @SpringBootTest
// @Transactional
@SpringIntegrationTest
class ArticleTest {

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
	 * 게시글 수정, 삭제 시 게시글 ID 미 존재 시 Exception 발생
	 */
	@Test
	@DisplayName("게시글 수정, 삭제 시 게시글 ID 미 존재 시 Exception 발생")
	void articleModifyIllegalArgumentArticleId() {
		articleDTO.setArticleId(null);
		assertThrows(IllegalArgumentException.class, () -> {
			articleService.modifyArticle(articleDTO);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			articleService.deleteArticle(articleDTO);
		});

	}

	/**
	 * 게시글 타입별 필수값 체크
	 */
	@Test
	@DisplayName("게시글 타입에 따른 필수값 체크 여부 확인")
	void articleNullable() {
		// 일상 게시글 ArticleType.NORMAl
		articleDTO.setArticleType(ArticleType.NORMAL);
		articleDTO.setContext(null);
		assertThrows(IllegalArgumentException.class, () -> {
			articleDTO.validateArticleNullable();
		});

		articleDTO.setContext("test context");
		// 커뮤니티 모집 게시글 ArticleType.GROUP
		articleDTO.setArticleType(ArticleType.GROUP);
		articleDTO.setGroupId(null);
		assertThrows(IllegalArgumentException.class, () -> {
			articleDTO.validateArticleNullable();
		});

		// 중고거래 게시글 ArticleType.TRADE
		articleDTO.setArticleType(ArticleType.TRADE);
		articleDTO.setPrice(null);
		assertThrows(IllegalArgumentException.class, () -> {
			articleDTO.validateArticleNullable();
		});

	}

	/**
	 * 게시글 조회
	 */
	@Test
	@DisplayName("게시글 ID 조회 및 전체 리스트 조회")
	void articleSearch() {
		// 9999 ID 저장
		articleService.createArticle(articleDTO);

		// articleId 로 조회
		int articleId = 1;
		Article findArticle = articleService.findById(articleId);
		assertThat(findArticle.getArticleId()).isEqualTo(articleId);

		List<Article> articles = articleService.findByAll();
		assertThat(articles).isNotNull();

	}

}