package com.danggeun.wish.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danggeun.wish.domain.Wish;

public interface WishJpaRepository extends JpaRepository<Wish, Integer> {

}
