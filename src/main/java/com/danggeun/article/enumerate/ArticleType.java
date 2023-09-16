package com.danggeun.article.enumerate;

public enum ArticleType {

	/**
	 * NOMAL : 일상 게시글
	 * GROUP : 커뮤니티 모집 게시글
	 * TRADE : 중고거래 게시글
	 */
	NOMAL(1),
	GROUP(2),
	TRADE(3);

	private int typeValue;

	ArticleType(int typeValue) {
		this.typeValue = typeValue;
	}

	public int getNum() {
		return this.typeValue;
	}

}
