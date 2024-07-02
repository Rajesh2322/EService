package com.es.api.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import com.es.api.dto.JwtTokenDto;
import com.es.api.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtUtil {

	@Value("${es.app.jwtSecret}")
	private String jwtSecret;

	@Value("${es.app.jwtCookieName}")
	private String jwtCookieName;
	
	@Value("${es.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	private final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	
	public String generateAccessToken(User user) {
		JwtBuilder builder = Jwts.builder().setSubject(user.getUserName()).setIssuedAt(Date.from(Instant.now()))
				                 .setExpiration(Date.from(Instant.now().plus(15,ChronoUnit.MINUTES))).signWith(SignatureAlgorithm.HS512, jwtSecret);
	 return builder.compact();
	}
	
	public boolean validate(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		}catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token - {} ", ex.getMessage());
		}catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token - {} ", ex.getMessage());
		}catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token - {} ", ex.getMessage());
		}catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty - {} ", ex.getMessage());
		}
		return false;
	}

	public JwtTokenDto getJwtTokenDto(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		
		return JwtTokenDto.builder()
				           .subject(claims.getSubject())
				           .expirationDate(claims.getExpiration())
				           .build();
	}
	
	public ResponseCookie getCleanJwtCookie() {
		ResponseCookie cookie = ResponseCookie.from(jwtCookieName, null).path("/user").build();
		return cookie;
	}
}
