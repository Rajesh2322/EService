package com.es.api.repository;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.es.api.entity.GivenAmount;

@Repository
public interface GivenAmountRepository extends JpaRepository<GivenAmount, Long>{


	List<GivenAmount> findByCreatedByIdAndCreatedDateBetween(Long userId, Date startDate, Date endDate);

	List<GivenAmount> findByCreatedDateBetween(Date startDate, Date endDate);


}
