package com.es.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceResponse {
	private String msg;
	private String data;

}
