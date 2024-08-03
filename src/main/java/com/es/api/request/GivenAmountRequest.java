package com.es.api.request;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

@Data
public class GivenAmountRequest {

	private Long serviceCodeId;
	private Map<Integer, Integer> counts;
	private Long userId;
	private BigDecimal given1Total;
	private BigDecimal given2Total;

}
