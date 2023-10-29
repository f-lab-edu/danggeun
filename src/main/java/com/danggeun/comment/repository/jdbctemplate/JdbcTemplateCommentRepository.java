package com.danggeun.comment.repository.jdbctemplate;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.danggeun.comment.dto.CommentRequestDto;
import com.danggeun.comment.dto.CommentResponseDto;
import com.danggeun.comment.dto.CommentResponseMapperImpl;
import com.danggeun.comment.repository.CommentRepository;

@Repository
public class JdbcTemplateCommentRepository implements CommentRepository {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert simpleJdbcInsert;

	private final CommentResponseMapperImpl commentResponseMapper;

	public JdbcTemplateCommentRepository(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("comment")
			.usingGeneratedKeyColumns("comment_id")
			.usingColumns("article_id", "context", "active", "registered_id", "modified_id");

		this.commentResponseMapper = new CommentResponseMapperImpl();
	}

	@Override
	public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
		// 파라미터 매칭
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("article_id", commentRequestDto.getArticleDto().getArticleId())
			.addValue("context", commentRequestDto.getContext())
			.addValue("active", commentRequestDto.isActive())
			.addValue("registered_id", commentRequestDto.getRegisteredId())
			.addValue("modified_id", commentRequestDto.getRegisteredId());

		// SimpleJdbcInsert 사용
		Number key = simpleJdbcInsert.executeAndReturnKey(param);
		commentRequestDto.setCommentId(key.longValue());

		return commentResponseMapper.toCommentResponseDto(commentRequestDto);
	}

	@Override
	public CommentResponseDto modifyComment(CommentRequestDto commentRequestDto) {
		// update sql
		StringBuilder sql = new StringBuilder("UPDATE comment SET ")
			.append("context = :context ,")
			.append("modified_date = now() ,")
			.append("modified_id = :modifiedId ")
			.append("WHERE article_id = :articleId ")
			.append("AND comment_id = :commentId");

		// 파라미터 매칭
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("commentId", commentRequestDto.getCommentId())
			.addValue("articleId", commentRequestDto.getArticleDto().getArticleId())
			.addValue("context", commentRequestDto.getContext())
			.addValue("modifiedId", commentRequestDto.getModifiedId());

		// DB 등록
		namedParameterJdbcTemplate.update(sql.toString(), param);

		return commentResponseMapper.toCommentResponseDto(commentRequestDto);
	}

	@Override
	public int deleteComment(CommentRequestDto commentRequestDto) {
		StringBuilder sql = new StringBuilder("UPDATE comment SET ")
			.append("active = 0 ,")
			.append("modified_date = now() ,")
			.append("modified_id = :modifiedId ")
			.append("WHERE article_id = :articleId ")
			.append("AND comment_id = :commentId");

		// 파라미터 매칭
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("commentId", commentRequestDto.getCommentId())
			.addValue("articleId", commentRequestDto.getArticleDto().getArticleId())
			.addValue("modifiedId", commentRequestDto.getModifiedId());

		// DB 등록
		return namedParameterJdbcTemplate.update(sql.toString(), param);
	}

	@Override
	public List<CommentResponseDto> findByAll(Pageable pageable, int articleId) {

		Sort.Order order =
			!pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Sort.Order.by("comment_id");
		int pageSize = pageable.getPageSize();
		long offSet = pageable.getOffset();
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("articleId", articleId)
			.addValue("order", order.getProperty())
			.addValue("orderDirection", order.getDirection().name())
			.addValue("pageSize", pageSize)
			.addValue("offSet", offSet);

		return namedParameterJdbcTemplate.query(
			"SELECT c.* FROM comment WHERE article_id = :articleId ORDER BY :order :orderDirection LIMIT :pageSize OFFSET :offSet",
			param, commentRowMapper());
	}

	private RowMapper<CommentResponseDto> commentRowMapper() {
		return BeanPropertyRowMapper.newInstance(CommentResponseDto.class);
	}

}
