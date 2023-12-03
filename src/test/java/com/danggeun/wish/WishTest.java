package com.danggeun.wish;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.danggeun.annotation.SpringIntegrationTest;
import com.danggeun.article.domain.Article;
import com.danggeun.article.dto.ArticleEntityMapperImpl;
import com.danggeun.article.dto.ArticleRequestDto;
import com.danggeun.article.enumerate.ArticleType;
import com.danggeun.article.repository.jpa.ArticleJpaRepository;
import com.danggeun.wish.domain.Wish;
import com.danggeun.wish.dto.WishEntityMapperImpl;
import com.danggeun.wish.dto.WishRequestDto;
import com.danggeun.wish.repository.jpa.WishJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringIntegrationTest
public class WishTest {

	@Autowired
	ArticleJpaRepository articleRepository;
	@PersistenceContext
	EntityManager entityManager;
	ArticleEntityMapperImpl articleEntityMapper = new ArticleEntityMapperImpl();

	@Autowired
	WishJpaRepository wishJpaRepository;

	WishEntityMapperImpl wishEntityMapper = new WishEntityMapperImpl();

	static Article article;

	@BeforeEach
	void setUp() {
		ArticleRequestDto articleRequestDTO = new ArticleRequestDto(
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
		article = articleRepository.save(articleEntityMapper.toArticleEntity(articleRequestDTO));
		entityManager.flush();
		entityManager.clear();
	}

	// 관심목록 등록
	@Test
	@DisplayName("관심목록 등록 테스트")
	void wishCreatedTest() {
		WishRequestDto wishRequestDto = new WishRequestDto();
		wishRequestDto.setArticleId(1);
		wishRequestDto.setUserId(1);

		Wish wishEntity = wishEntityMapper.toWishEntity(wishRequestDto);
		wishEntity.setArticle(article);

		Wish save = wishJpaRepository.save(wishEntity);
		assertThat(save.getWishId()).isNotNull();
	}

	@Test
	@DisplayName("관심목록 등록 시 게시글 미 존재 시 오류 발생")
	void wishCreatedExceptionTest() {
		WishRequestDto wishRequestDto = new WishRequestDto();
		wishRequestDto.setUserId(1);

		Wish wishEntity = wishEntityMapper.toWishEntity(wishRequestDto);
		wishEntity.setArticle(new Article());

		assertThatThrownBy(() -> {
			wishJpaRepository.save(wishEntity);
		}).isInstanceOf(InvalidDataAccessApiUsageException.class);

	}

	// 관심목록 조회
	@Test
	@DisplayName("관심목록 조회 테스트")
	void wishSearchTest() {
		for (int i = 0; i < 10; i++) {
			WishRequestDto wishRequestDto = new WishRequestDto();
			wishRequestDto.setArticleId(1);
			wishRequestDto.setUserId(i);

			Wish wishEntity = wishEntityMapper.toWishEntity(wishRequestDto);
			wishEntity.setArticle(article);

			Wish save = wishJpaRepository.save(wishEntity);
		}
		entityManager.flush();
		entityManager.clear();

		Page<Wish> all = wishJpaRepository.findAll(PageRequest.of(0, 3));
		all.forEach(
			wish -> {
				assertThat(wish).isNotNull();
			}
		);
		assertThat(all.get().count()).isEqualTo(3L);
		List<Wish> content = all.getContent();
		assertThat(content.size()).isEqualTo(3);

	}

}
