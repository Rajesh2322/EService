package com.es.api.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.es.api.dto.JwtTokenDto;
import com.es.api.dto.Principal;
import com.es.api.util.JwtUtil;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		final String token = header.split(" ")[1].trim();
		if(!jwtUtil.validate(token)) {
			filterChain.doFilter(request, response);
			return;
			
		}
		JwtTokenDto jwtTokenDto = jwtUtil.getJwtTokenDto(token);
		List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
		
		Principal principal = new Principal();
		principal.setToken(token);
		principal.setUserName(jwtTokenDto.getSubject());
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, null, grantedAuthorityList);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
	}

}
