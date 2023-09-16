package com.danggeun.article.repository.jdbctemplate;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.danggeun.article.dto.ArticleDTO;
import com.danggeun.article.enumerate.ArticleType;
import com.danggeun.article.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateArticleRepository implements ArticleRepository {

	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * 게시글 생성
	 * @param article
	 * @return ArticleDTO
	 */
	@Override
	public ArticleDTO createArticle(ArticleDTO article) {
		// insert sql
		StringBuilder sql = new StringBuilder("INSERT INTO article")
			.append(
				"(article_id, user_id, comment_id, region_id, group_id, subject, context, article_gb, price, active, reg_dt, reg_id, mod_dt, mod_id)")
			.append("VALUES")
			.append(
				"(:articleId, :userId, :commentId, :regionId, :groupId, :subject, :context, :articleType, :price, :active, now(), :regId, now(), :modId)");

		// 파라미터 매칭
		// articleType enum으로 인해서 BeanPropertySqlParameterSource 보다는 Map으로 직접 지정
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("articleId", article.getArticleId())
			.addValue("userId", article.getUserId())
			.addValue("commentId", article.getCommentId())
			.addValue("regionId", article.getRegionId())
			.addValue("groupId", article.getGroupId())
			.addValue("subject", article.getSubject())
			.addValue("context", article.getContext())
			.addValue("articleType", article.getArticleType().getNum())
			.addValue("price", article.getPrice())
			.addValue("active", article.isActive())
			.addValue("regId", article.getRegId())
			.addValue("modId", article.getModId());

		// DB 등록
		save(sql.toString(), param, this.getClass().getSimpleName());

		return article;
	}

	/**
	 * 게시글 수정
	 * @param article
	 * @return ArticleDTO
	 */
	@Override
	public ArticleDTO modifyArticle(ArticleDTO article) {
		// update sql
		StringBuilder sql = new StringBuilder("UPDATE article SET ")
			.append("subject = :subject ,")
			.append("context = :context ,")
			.append("mod_dt = now() ,")
			.append("mod_id = :modId ")
			.append("WHERE article_id = :articleId");

		// 파라미터 매칭
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);

		// DB 등록
		save(sql.toString(), param, this.getClass().getSimpleName());

		return article;
	}

	/**
	 * 게시글 삭제
	 * @param article
	 * @return
	 */
	@Override
	public ArticleDTO deleteArticle(ArticleDTO article) {
		StringBuilder sql = new StringBuilder("UPDATE article SET ")
			.append("active = 0 ,")
			.append("mod_dt = now() ,")
			.append("mod_id = :modId ")
			.append("WHERE article_id = :articleId");

		// 파라미터 매칭
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);

		// DB 등록
		save(sql.toString(), param, this.getClass().getSimpleName());

		return article;
	}

	/**
	 * 게시글 시퀀스 생성
	 * @param prefix
	 * @param name
	 * @return String
	 */
	@Override
	public String newSequence(String prefix, String name) {
		// 시퀀스 생성 SQL
		// prefix = ARTICLE / name = article_sequence
		String sequenceSql = "SELECT CONCAT(?, DATE_FORMAT(NOW(),'%Y%m%d'), LPAD(nextval(?), '6' ,'0')) AS sequence FROM dual";
		String sequence = jdbcTemplate.queryForObject(sequenceSql, String.class, prefix, name);
		if (!StringUtils.hasText(sequence)) {
			throw new IllegalStateException("게시글 시퀀스 생성 실패 하였습니다.");
		}
		return sequence;
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
	 * CRUD 중복 내용 메소드
	 * @param sql
	 * @param param
	 * @param name
	 */
	private void save(String sql, SqlParameterSource param, String name) {
		int result = 0;
		result = namedParameterJdbcTemplate.update(sql, param);

		if (result == 0) {
			throw new IllegalStateException("게시글 " + name + " 실패 하였습니다.");
		}
	}

	private RowMapper<ArticleDTO> articleRowMapper() {
		return (rs, rowNum) -> {
			ArticleDTO articleDTO = new ArticleDTO();
			articleDTO.setArticleId(rs.getString("article_id"));
			articleDTO.setUserId(rs.getString("user_id"));
			articleDTO.setCommentId(rs.getString("comment_id"));
			articleDTO.setRegionId(rs.getString("region_id"));
			articleDTO.setGroupId(rs.getString("group_id"));
			articleDTO.setSubject(rs.getString("subject"));
			articleDTO.setContext(rs.getString("context"));
			articleDTO.setArticleType(validateArticleType(rs.getInt("article_gb")));
			articleDTO.setPrice(rs.getInt("price"));
			articleDTO.setActive(rs.getBoolean("active"));
			articleDTO.setRegDt(rs.getDate("reg_dt"));
			articleDTO.setRegId(rs.getString("reg_id"));
			articleDTO.setModDt(rs.getDate("mod_dt"));
			articleDTO.setModId(rs.getString("mod_id"));
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
