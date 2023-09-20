package com.danggeun.article.dto;

import java.lang.reflect.Field;
import java.util.Date;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.danggeun.article.enumerate.ArticleType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {

	private String articleId;
	private String userId;
	private String commentId;
	private String regionId;
	private String groupId;
	private String subject;
	private String context;
	private ArticleType articleType;
	private int price;
	private boolean active;
	private Date registeredDate;
	private String registeredId;
	private Date modifiedDate;
	private String modifiedId;

	public void hasId() {
		if (!StringUtils.hasText(this.getArticleId())) {
			throw new IllegalArgumentException("게시글 ID가 없습니다.");
		}
	}

	private void hasField(ArticleDTO articleDTO, String... valuse) {
		String[] colNames = valuse;
		Field field = null;
		Object value = null;

		try {
			for (String colName : colNames) {
				field = articleDTO.getClass().getDeclaredField(colName);
				value = field.get(articleDTO);

				if (ObjectUtils.isEmpty(value)) {
					throw new IllegalArgumentException("게시글 " + colName + "필수값 NULL 입니다.");
				}
			}
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

	}

	public void validateArticleNullable(ArticleDTO articleDTO) {
		switch (articleDTO.getArticleType()) {
			case NOMAL:
				hasField(articleDTO, "articleId", "userId", "subject", "context", "regionId");
				break;
			case GROUP:
				hasField(articleDTO, "articleId", "userId", "subject", "context", "regionId", "groupId");
				break;
			case TRADE:
				hasField(articleDTO, "articleId", "userId", "subject", "context", "regionId", "price");
				break;
			default:
				throw new IllegalArgumentException("정상적인 게시글 타입이 아닙니다.");
		}

	}

}
