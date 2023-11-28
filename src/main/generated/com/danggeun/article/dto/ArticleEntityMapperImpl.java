package com.danggeun.article.dto;

import com.danggeun.article.domain.Article;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-28T16:34:00+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
public class ArticleEntityMapperImpl implements ArticleEntityMapper {

    @Override
    public Article toArticleEntity(ArticleRequestDto articleRequestDto) {
        if ( articleRequestDto == null ) {
            return null;
        }

        Article article = new Article();

        article.setArticleId( articleRequestDto.getArticleId() );
        article.setUserId( articleRequestDto.getUserId() );
        article.setCommentId( articleRequestDto.getCommentId() );
        article.setRegionId( articleRequestDto.getRegionId() );
        article.setGroupId( articleRequestDto.getGroupId() );
        article.setSubject( articleRequestDto.getSubject() );
        article.setContext( articleRequestDto.getContext() );
        article.setArticleType( articleRequestDto.getArticleType() );
        article.setPrice( articleRequestDto.getPrice() );
        article.setActive( articleRequestDto.isActive() );

        return article;
    }
}
