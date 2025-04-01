package com.es.api.service.impl;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.es.api.entity.User;
import com.es.api.exception.BadRequestException;
import com.es.api.repository.UserRepository;
import com.es.api.request.LoginRequest;
import com.es.api.request.ResetPasswordRequest;
import com.es.api.request.UserRequest;
import com.es.api.service.UserService;
import com.es.api.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public String create(UserRequest user) {
		Optional<User> optionalUser = userRepo.findByUserName(user.getUserName());
		if (optionalUser.isPresent()) {
			throw new BadRequestException("User name already exists. Please try again with a different user name!");
		}
		if (user.getRole().equalsIgnoreCase("Admin")) {
			Optional<User> adminUser = userRepo.findByRoleIgnoreCase("Admin");
			if (adminUser.isPresent()) {
				throw new BadRequestException("There should not be more than one user with the role 'Admin'.");
			}
		}
		User userDetails = User.builder().name(user.getName()).userName(user.getUserName())
				.password(passwordEncoder.encode(user.getPassword())).role(user.getRole())
				.createdBy(user.getCreatedBy()).createdOn(new Date()).build();
		return userRepo.save(userDetails).getUserName();
	}

	@Override
	public Optional<User> findById(String userName) {
		Optional<User> user = userRepo.findByUserName(userName);
		return user;
	}

	@Override
	public String login(LoginRequest loginRequest) {
		Optional<User> optionalUser = userRepo.findByUserName(loginRequest.getUserName());
		if (optionalUser.isEmpty()) {
			throw new BadRequestException("User Not Found !");
		}
		if (passwordEncoder.matches(loginRequest.getPassword(), optionalUser.get().getPassword())) {
			return jwtUtil.generateAccessToken(optionalUser.get());
		} else {
			throw new BadRequestException("Invalid UserName or Password");
		}
	}

	@Override
	public ResponseCookie logOut() {
		ResponseCookie cookie = jwtUtil.getCleanJwtCookie();
		return cookie;
	}

	@Override
	public String resetPassword(ResetPasswordRequest resetPasswordRequest) throws BadRequestException {
		Optional<User> optionalUser = userRepo.findByUserName(resetPasswordRequest.getUserName());

		if (!optionalUser.isPresent()) {
			throw new BadRequestException("User not found");
		}

		User user = optionalUser.get();

		// Verify the old password (assuming it's stored as a hashed value)
		if (!passwordEncoder.matches(resetPasswordRequest.getOldPassword(), user.getPassword())) {
	        throw new BadRequestException("Old password is incorrect");
	    }

	    // Encode the new password and update the user entity
	    user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
		userRepo.save(user);

		return "Password reset successfully";
	}

}
