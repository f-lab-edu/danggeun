package com.danggeun.wish.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.danggeun.wish.domain.Wish;
import com.danggeun.wish.dto.WishResponseDto;

public interface WishJpaRepository extends JpaRepository<Wish, Integer> {
	@Query("select w from Wish w")
	Page<WishResponseDto> findAllDto(Pageable pageable);

	@Query("select w from Wish w where w.userId = :userId")
	Page<WishResponseDto> findByUserId(Pageable pageable, @Param("userId") Integer userId);
}
