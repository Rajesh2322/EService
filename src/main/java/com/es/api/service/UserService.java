package com.es.api.service;

import java.util.Optional;

import org.springframework.http.ResponseCookie;

import com.es.api.entity.User;
import com.es.api.request.LoginRequest;
import com.es.api.request.ResetPasswordRequest;
import com.es.api.request.UserRequest;

public interface UserService {

	String create(UserRequest user);

	Optional<User> findById(String userName);

	String login(LoginRequest loginRequest);

	ResponseCookie logOut();

	String resetPassword(ResetPasswordRequest resetPasswordRequest);

}
