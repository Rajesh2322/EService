package com.es.api.request;

import java.util.List;

import lombok.Data;

@Data
public class GroupedBillsRequest {
	
	private List<String> serviceNames;
    private Long userId;

}
