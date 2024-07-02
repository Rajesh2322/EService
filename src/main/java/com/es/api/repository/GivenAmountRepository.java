package com.es.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.es.api.entity.GivenAmount;

@Repository
public interface GivenAmountRepository extends JpaRepository<GivenAmount, Long>{

}
