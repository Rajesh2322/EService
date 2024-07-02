package com.es.api.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenDto {
	
	private String subject;
	private Date expirationDate;

}
