package com.danggeun.wish.repository.jdbctemplate;

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

import com.danggeun.wish.dto.WishRequestDto;
import com.danggeun.wish.dto.WishResponseDto;
import com.danggeun.wish.dto.WishResponseMapperImpl;
import com.danggeun.wish.repository.WishRepository;

@Repository
public class JdbcTemplateWishRepository implements WishRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert simpleJdbcInsert;

	private final WishResponseMapperImpl wishResponseMapper;

	public JdbcTemplateWishRepository(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("wish")
			.usingGeneratedKeyColumns("wish_id")
			.usingColumns("article_id", "active", "registered_id", "modified_id");

		this.wishResponseMapper = new WishResponseMapperImpl();
	}

	@Override
	public WishResponseDto createWish(WishRequestDto wishRequestDto) {
		// 파라미터 매칭
		SqlParameterSource param = new MapSqlParameterSource()
			// .addValue("article_id", wishRequestDto.getArticleDto().getArticleId())
			.addValue("active", wishRequestDto.isActive());
		// .addValue("registered_id", wishRequestDto.getRegisteredId())
		// .addValue("modified_id", wishRequestDto.getRegisteredId());

		Number key = simpleJdbcInsert.executeAndReturnKey(param);
		wishRequestDto.setWishId(key.intValue());

		return wishResponseMapper.toWishResponseDto(wishRequestDto);
	}

	@Override
	public int modifyWish(Long wishId) {
		StringBuilder sql = new StringBuilder("UPDATE wish SET ")
			.append("active = :active, ")
			.append("modified_date = now() ")
			.append("WHERE wish_id = :wishId ");

		// 파라미터 매칭
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("wishId", wishId)
			.addValue("active", true);

		// DB 등록
		return namedParameterJdbcTemplate.update(sql.toString(), param);
	}

	@Override
	public int deleteWish(Long wishId) {
		StringBuilder sql = new StringBuilder("UPDATE wish SET ")
			.append("active = :active, ")
			.append("modified_date = now() ")
			.append("WHERE wish_id = :wishId ");

		// 파라미터 매칭
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("wishId", wishId)
			.addValue("active", false);

		// DB 등록
		return namedParameterJdbcTemplate.update(sql.toString(), param);
	}

	@Override
	public WishResponseDto findById(Long wishId) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("wishId", wishId);
		return namedParameterJdbcTemplate.queryForObject(
			"SELECT * FROM wish WHERE wish_id = :wishId"
			, param
			, wishRowMapper());
	}

	@Override
	public List<WishResponseDto> findByAll(Pageable pageable) {
		Sort.Order order =
			!pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Sort.Order.by("wish_id");
		int pageSize = pageable.getPageSize();
		long offSet = pageable.getOffset();
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("order", order.getProperty())
			.addValue("orderDirection", order.getDirection().name())
			.addValue("pageSize", pageSize)
			.addValue("offSet", offSet);

		return namedParameterJdbcTemplate.query(
			"SELECT w.* FROM wish w ORDER BY :order :orderDirection LIMIT :pageSize OFFSET :offSet",
			param, wishRowMapper());
	}

	@Override
	public List<WishResponseDto> findByUserId(Pageable pageable, Long userId) {
		Sort.Order order =
			!pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Sort.Order.by("wish_id");
		int pageSize = pageable.getPageSize();
		long offSet = pageable.getOffset();
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("registeredId", userId)
			.addValue("order", order.getProperty())
			.addValue("orderDirection", order.getDirection().name())
			.addValue("pageSize", pageSize)
			.addValue("offSet", offSet);

		return namedParameterJdbcTemplate.query(
			"SELECT w.* FROM wish w WHERE w.registered_id = :registeredId ORDER BY :order :orderDirection LIMIT :pageSize OFFSET :offSet",
			param, wishRowMapper());
	}

	private RowMapper<WishResponseDto> wishRowMapper() {
		return BeanPropertyRowMapper.newInstance(WishResponseDto.class);
	}
}
