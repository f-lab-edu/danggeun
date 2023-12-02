package com.danggeun.article;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.danggeun.annotation.SpringIntegrationTest;
import com.danggeun.article.domain.Article;
import com.danggeun.article.dto.ArticleEntityMapperImpl;
import com.danggeun.article.dto.ArticleRequestDto;
import com.danggeun.article.enumerate.ArticleType;
import com.danggeun.article.exception.ArticleNotFoundException;
import com.danggeun.article.repository.jpa.ArticleJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringIntegrationTest
class ArticleTest {

	@Autowired
	ArticleJpaRepository articleRepository;
	@PersistenceContext
	EntityManager entityManager;
	ArticleEntityMapperImpl articleEntityMapper = new ArticleEntityMapperImpl();

	public static ArticleRequestDto articleRequestDTO;

	@BeforeEach
	void setUp() {
		articleRequestDTO = new ArticleRequestDto(
			null,
			9999,
			9999,
			null,
			"test subject",
			"test context",
			ArticleType.NORMAL,
			null,
			true,
			null,
			"testDevelop",
			null,
			"testDevelop"
		);
	}

	/**
	 * 게시글 신규 등록
	 * 1. 신규 생성 정상 작동 시 생성 되는 ArticleId 가 있어야 한다.
	 */
	@Test
	@DisplayName("게시글 신규 등록")
	void articleCreatedTest() {
		Article createArticle = articleEntityMapper.toArticleEntity(articleRequestDTO);
		articleRepository.save(createArticle);

		assertThat(createArticle.getArticleId()).isNotNull();
	}

	/**
	 * 게시글 ID 미 존재 시 Exception 발생
	 */
	@Test
	@DisplayName("게시글 ID 미 존재 시 Exception 발생")
	void validateArticleId() {
		assertThatThrownBy(() -> articleRequestDTO.validateId())
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("%s", "게시글 ID가 없습니다.");
	}

	/**
	 * 게시글 타입별 필수값 체크
	 */
	@ParameterizedTest
	@DisplayName("게시글 타입에 따른 필수값 체크 여부 확인")
	@MethodSource("data")
	void validateArticleNullable(ArticleType type) {

		articleRequestDTO.setArticleType(type);
		switch (type) {
			case NORMAL -> articleRequestDTO.setContext(null);
			case GROUP -> articleRequestDTO.setGroupId(null);
			case TRADE -> articleRequestDTO.setPrice(null);
		}
		assertThatThrownBy(() -> articleRequestDTO.validateArticleNullable())
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("%s", "게시글 필수값 NULL 입니다.");
	}

	static Stream<Arguments> data() {
		return Stream.of(
			Arguments.of(ArticleType.NORMAL),
			Arguments.of(ArticleType.GROUP),
			Arguments.of(ArticleType.TRADE)
		);
	}

	/**
	 * 게시글 조회
	 */
	@Test
	@DisplayName("게시글 ID 조회 및 전체 리스트 조회")
	void articleSearch() {
		int[] articleIds = new int[4];
		// 조회할 게시글 등록 처리
		for (int i = 0; i < 4; i++) {
			Article createArticle = articleEntityMapper.toArticleEntity(articleRequestDTO);
			articleRepository.save(createArticle);
			articleIds[i] = createArticle.getArticleId();
		}

		entityManager.flush();
		entityManager.clear();

		// 조회
		for (int articleId : articleIds) {
			assertThat(articleRepository.findById(articleId).isPresent()).isTrue();
		}

		// 페이징 처리 객체 생성
		Pageable pageable = PageRequest.of(0, 3);
		Page<Article> articles = articleRepository.findAll(pageable);

		assertThat(articles.getTotalElements()).isEqualTo(4L);
		assertThat(articles.get().count()).isEqualTo(3L);
	}

	@Test
	@DisplayName("게시글 조회 시 존재 하지 않는 경우 예외 발생")
	void articleNotFound() {
		assertThat(articleRepository.findById(99999).isEmpty()).isTrue();
		assertThatThrownBy(() ->
			articleRepository.findById(99999).orElseThrow(ArticleNotFoundException::new))
			.isInstanceOf(ArticleNotFoundException.class);
	}

}