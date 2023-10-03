package com.danggeun.article.repository.jdbctemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.danggeun.article.domain.Article;
import com.danggeun.article.dto.ArticleDTO;
import com.danggeun.article.repository.ArticleRepository;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert simpleJdbcInsert;

	public JdbcTemplateArticleRepository(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("article")
			.usingGeneratedKeyColumns("article_id")
			.usingColumns("user_id", "comment_id", "region_id", "group_id", "subject", "context", "article_type",
				"price", "active", "registered_id", "modified_id");
	}

	/**
	 * 게시글 생성
	 * @param article
	 * @return Article
	 */
	@Override
	public ArticleDTO createArticle(ArticleDTO article) {
		// SimpleJdbcInsert 으로 사용

		// 파라미터 매칭
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("userId", article.getUserId())
			.addValue("commentId", article.getCommentId())
			.addValue("regionId", article.getRegionId())
			.addValue("groupId", article.getGroupId())
			.addValue("subject", article.getSubject())
			.addValue("context", article.getContext())
			.addValue("articleType", article.getArticleType().getNum())
			.addValue("price", article.getPrice())
			.addValue("active", article.isActive())
			.addValue("registeredId", article.getRegisteredId())
			.addValue("modifiedId", article.getRegisteredId());

		Number key = simpleJdbcInsert.executeAndReturnKey(param);
		article.setArticleId(key.intValue());

		return article;
	}

	/**
	 * 게시글 수정
	 * @param article
	 * @return int
	 */
	@Override
	public int modifyArticle(ArticleDTO article) {
		// update sql
		StringBuilder sql = new StringBuilder("UPDATE article SET ")
			.append("subject = :subject ,")
			.append("context = :context ,")
			.append("modified_date = now() ,")
			.append("modified_id = :modifiedId ")
			.append("WHERE article_id = :articleId");

		// 파라미터 매칭
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);

		// DB 등록
		return namedParameterJdbcTemplate.update(sql.toString(), param);

	}

	/**
	 * 게시글 삭제
	 * @param article
	 * @return int
	 */
	@Override
	public int deleteArticle(ArticleDTO article) {
		StringBuilder sql = new StringBuilder("UPDATE article SET ")
			.append("active = 0 ,")
			.append("modified_date = now() ,")
			.append("modified_id = :modifiedId ")
			.append("WHERE article_id = :articleId");

		// 파라미터 매칭
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);

		// DB 등록
		return namedParameterJdbcTemplate.update(sql.toString(), param);
	}

	/**
	 * 게시글 ID 조회
	 * @param id
	 * @return Optional<Article>
	 */
	@Override
	public Optional<Article> findById(int id) {
		try {
			Map<String, Object> param = Map.of("articleId", id);
			Article article = namedParameterJdbcTemplate.queryForObject(
				"SELECT * FROM article WHERE article_id = :articleId", param,
				articleRowMapper());
			return Optional.of(article);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	/**
	 * 게시글 전제 조회
	 * @return
	 */
	@Override
	public List<Article> findByAll() {
		return namedParameterJdbcTemplate.query("SELECT * FROM article", articleRowMapper());
	}

	private RowMapper<Article> articleRowMapper() {
		// snake_case -> camelCase 변환 처리 되어 진행
		return BeanPropertyRowMapper.newInstance(Article.class);
	}

}
