package com.es.api.request;

import java.util.Map;

import lombok.Data;

@Data
public class GivenAmountRequest {

	private Long serviceCodeId;
	 private Map<Integer, Integer> counts;
	 private Long userId;
	 
	 
}
