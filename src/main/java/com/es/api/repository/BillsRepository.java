package com.es.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.es.api.entity.Bills;

@Repository
public interface BillsRepository extends JpaRepository<Bills,Long>{

	List<Bills> findByUserIdIdOrderByCreatedDateDesc(Long userId);

	List<Bills> findAllByOrderByCreatedDateDesc();


	List<Bills> findByServiceNameInAndUserIdId(List<String> serviceNames, Long userId);

	List<Bills> findByServiceNameIn(List<String> serviceNames);

}
