package com.es.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.es.api.entity.User;
import com.es.api.exception.BadRequestException;
import com.es.api.request.LoginRequest;
import com.es.api.request.UserRequest;
import com.es.api.response.Response;
import com.es.api.response.ServiceResponse;
import com.es.api.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/createUser")
	public ResponseEntity<?> createUser(@RequestBody UserRequest user){
		try {
	    log.info("Create User service - start");
	    log.info("Received UserRequest: {}", user);

	    String userName = userService.create(user);
	    log.info("Created user: {}", userName);

	    if (userName == null) {
	        log.error("userService.create returned null");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("userService.create returned null");
	    }

	    return ResponseEntity.ok().body(new ResponseEntity<>(ServiceResponse.builder()
	        .data(userName)
	        .msg("User created Successfully !")
	        .build(), HttpStatus.CREATED));
		}catch (BadRequestException e) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
		            Response.builder().responseMsg(e.getMsg()).build());
		}
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
		 try {
		String  token = userService.login(loginRequest);
		
		return ResponseEntity.ok().body(new ResponseEntity<>(
				Response.builder().token(token).responseMsg("Login Successfully.").build(),HttpStatus.OK));
		 } catch (BadRequestException e) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
		            Response.builder().responseMsg(e.getMsg()).build());
		    }
	}

	 @GetMapping("/getUserByName")
	    public ResponseEntity< ResponseEntity<Optional<User>>> getUserById(String userName) {
	        log.info("Get User by ID service - start");
	        log.info("Received User Name: {}", userName);

	        Optional<User> user = userService.findById(userName);

	        log.info("Retrieved user details: {}", user);
	        return ResponseEntity.ok().body(new ResponseEntity<>(user, HttpStatus.OK));
	    }
	
	 @PostMapping("/logOut")
	 public ResponseEntity<?> logOut(){
		 ResponseCookie cookie = userService.logOut();
		 return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				 .body(new ResponseEntity<>(ServiceResponse.builder().msg("You've been signed out !!").build(),HttpStatus.OK));
	 }

}
