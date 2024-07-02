package com.es.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.es.api.entity.DigitalServices;

@Repository
public interface DigitalServicesRepository extends JpaRepository<DigitalServices, Long>{


}
