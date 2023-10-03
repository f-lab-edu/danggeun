package com.danggeun.article.repository.jdbctemplate;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.danggeun.article.dto.ArticleDTO;
import com.danggeun.article.enumerate.ArticleType;
import com.danggeun.article.repository.ArticleRepository;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {

	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert simpleJdbcInsert;

	public JdbcTemplateArticleRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
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
		return save(sql.toString(), param, this.getClass().getSimpleName());

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
		return save(sql.toString(), param, this.getClass().getSimpleName());

	}

	/**
	 * 게시글 ID 조회
	 * @param id
	 * @return Optional<ArticleDTO>
	 */
	@Override
	public Optional<ArticleDTO> findById(String id) {
		List<ArticleDTO> result = jdbcTemplate.query("SELECT * FROM article WHERE article_id = ?", articleRowMapper(),
			id);
		return result.stream().findAny();
	}

	/**
	 * 게시글 전제 조회
	 * @return
	 */
	@Override
	public List<ArticleDTO> findByAll() {
		return jdbcTemplate.query("SELECT * FROM article", articleRowMapper());
	}

	/**
	 * CRUD 중복 내용 메소드
	 * @param sql
	 * @param param
	 * @param name
	 */
	private int save(String sql, SqlParameterSource param, String name) {
		int result = 0;
		result = namedParameterJdbcTemplate.update(sql, param);

		if (result == 0) {
			throw new IllegalStateException("게시글 " + name + " 실패 하였습니다.");
		}

		return result;
	}

	private RowMapper<ArticleDTO> articleRowMapper() {
		// BeanPropertyRowMapper 사용 테스트 해볼 것...
		return (rs, rowNum) -> {
			ArticleDTO articleDTO = new ArticleDTO();
			articleDTO.setArticleId(rs.getInt("article_id"));
			articleDTO.setUserId(rs.getInt("user_id"));
			articleDTO.setCommentId(rs.getInt("comment_id"));
			articleDTO.setRegionId(rs.getInt("region_id"));
			articleDTO.setGroupId(rs.getInt("group_id"));
			articleDTO.setSubject(rs.getString("subject"));
			articleDTO.setContext(rs.getString("context"));
			articleDTO.setArticleType(validateArticleType(rs.getInt("article_gb")));
			articleDTO.setPrice(rs.getInt("price"));
			articleDTO.setActive(rs.getBoolean("active"));
			articleDTO.setRegisteredDate(rs.getDate("registered_date"));
			articleDTO.setRegisteredId(rs.getString("registered_id"));
			articleDTO.setModifiedDate(rs.getDate("modified_date"));
			articleDTO.setModifiedId(rs.getString("modified_id"));
			return articleDTO;
		};
	}

	/**
	 * ArticleType
	 * @param num
	 * @return ArticleType
	 */
	private ArticleType validateArticleType(int num) {
		for (ArticleType type : ArticleType.values()) {
			if (type.getNum() == num) {
				return type;
			}
		}
		return null;
	}
}
