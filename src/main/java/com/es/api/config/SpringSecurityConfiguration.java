package com.es.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.es.api.filter.JwtSecurityFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfiguration {

    @Autowired
    private JwtSecurityFilter jwtSecurityFilter;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity = httpSecurity.cors().and().csrf().disable();
        httpSecurity = httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
        httpSecurity.authorizeRequests().antMatchers("/user/**").permitAll().anyRequest().authenticated();
        
        httpSecurity.addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);
        
        return httpSecurity.build();
        
    }
}
