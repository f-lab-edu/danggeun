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

import com.danggeun.article.dto.ArticleRequestDto;
import com.danggeun.article.dto.ArticleResponseDto;
import com.danggeun.article.dto.ArticleResponseMapperImpl;
import com.danggeun.article.repository.ArticleRepository;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert simpleJdbcInsert;

	// ArticleRequsetDto -> ArticleResponseDto 변환기
	private final ArticleResponseMapperImpl articleResponseMapper;

	public JdbcTemplateArticleRepository(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("article")
			.usingGeneratedKeyColumns("article_id")
			.usingColumns("user_id", "comment_id", "region_id", "group_id", "subject", "context", "article_type",
				"price", "active", "registered_id", "modified_id");

		this.articleResponseMapper = new ArticleResponseMapperImpl();
	}

	/**
	 * 게시글 생성
	 * @param articleRequestDto
	 * @return ArticleResponseDto
	 */
	@Override
	public ArticleResponseDto createArticle(ArticleRequestDto articleRequestDto) {
		// 파라미터 매칭
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("userId", articleRequestDto.getUserId())
			.addValue("commentId", articleRequestDto.getCommentId())
			.addValue("regionId", articleRequestDto.getRegionId())
			.addValue("groupId", articleRequestDto.getGroupId())
			.addValue("subject", articleRequestDto.getSubject())
			.addValue("context", articleRequestDto.getContext())
			.addValue("articleType", articleRequestDto.getArticleType().getNum())
			.addValue("price", articleRequestDto.getPrice())
			.addValue("active", articleRequestDto.isActive())
			.addValue("registeredId", articleRequestDto.getRegisteredId())
			.addValue("modifiedId", articleRequestDto.getRegisteredId());

		// SimpleJdbcInsert 사용
		Number key = simpleJdbcInsert.executeAndReturnKey(param);
		articleRequestDto.setArticleId(key.intValue());

		return articleResponseMapper.toArticleResponseDto(articleRequestDto);
	}

	/**
	 * 게시글 수정
	 * @param articleRequestDto
	 * @return ArticleResponseDto
	 */
	@Override
	public ArticleResponseDto modifyArticle(ArticleRequestDto articleRequestDto) {
		// update sql
		StringBuilder sql = new StringBuilder("UPDATE article SET ")
			.append("subject = :subject ,")
			.append("context = :context ,")
			.append("modified_date = now() ,")
			.append("modified_id = :modifiedId ")
			.append("WHERE article_id = :articleId");

		// 파라미터 매칭
		SqlParameterSource param = new BeanPropertySqlParameterSource(articleRequestDto);

		// DB 등록
		namedParameterJdbcTemplate.update(sql.toString(), param);

		return articleResponseMapper.toArticleResponseDto(articleRequestDto);
	}

	/**
	 * 게시글 삭제
	 * @param articleRequestDto
	 * @return int
	 */
	@Override
	public int deleteArticle(ArticleRequestDto articleRequestDto) {
		StringBuilder sql = new StringBuilder("UPDATE article SET ")
			.append("active = 0 ,")
			.append("modified_date = now() ,")
			.append("modified_id = :modifiedId ")
			.append("WHERE article_id = :articleId");

		// 파라미터 매칭
		SqlParameterSource param = new BeanPropertySqlParameterSource(articleRequestDto);

		// DB 등록
		return namedParameterJdbcTemplate.update(sql.toString(), param);
	}

	/**
	 * 게시글 ID 조회
	 * @param articleId
	 * @return Optional<ArticleResponseDto>
	 */
	@Override
	public Optional<ArticleResponseDto> findById(int articleId) {
		try {
			Map<String, Object> param = Map.of("articleId", articleId);
			ArticleResponseDto articleResponseDto = namedParameterJdbcTemplate.queryForObject(
				"SELECT * FROM article WHERE article_id = :articleId", param, articleRowMapper());
			return Optional.ofNullable(articleResponseDto);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	/**
	 * 게시글 전제 조회
	 * @return List<ArticleResponseDto>
	 */
	@Override
	public List<ArticleResponseDto> findByAll() {
		return namedParameterJdbcTemplate.query("SELECT * FROM article", articleRowMapper());
	}

	/**
	 * resultSet 결과 값 리터 처리 및 snake_case -> camelCase 변환 처리
	 * @return RowMapper<ArticleResponseDto>
	 */
	private RowMapper<ArticleResponseDto> articleRowMapper() {
		return BeanPropertyRowMapper.newInstance(ArticleResponseDto.class);
	}

}
