package com.es.api.service;

import java.util.List;

import com.es.api.entity.Bills;
import com.es.api.request.BillRequest;

public interface BillsService {

	Long create(BillRequest billRequest);

	List<Bills> findAllByUserId(Long userId);

}
