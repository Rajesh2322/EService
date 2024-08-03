package com.es.api.service;

import java.util.List;

import com.es.api.request.GivenAmountRequest;
import com.es.api.request.GroupedBillsRequest;
import com.es.api.response.BillsGroupedByService;

public interface AmountService {

	String create(GivenAmountRequest givenAmountRequest);


	List<BillsGroupedByService> findBillsGroupedByServiceName(GroupedBillsRequest request);

}
