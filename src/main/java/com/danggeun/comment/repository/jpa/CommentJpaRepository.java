package com.danggeun.comment.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danggeun.comment.domain.Comment;

public interface CommentJpaRepository extends JpaRepository<Comment, Integer> {

}
