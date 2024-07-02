package com.es.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response<T> {
	private String responseMsg;
	private T token;
	private String data;

}
