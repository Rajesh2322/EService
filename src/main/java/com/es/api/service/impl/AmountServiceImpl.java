package com.es.api.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.api.entity.DigitalServices;
import com.es.api.entity.GivenAmount;
import com.es.api.entity.User;
import com.es.api.exception.BadRequestException;
import com.es.api.repository.DigitalServicesRepository;
import com.es.api.repository.GivenAmountRepository;
import com.es.api.repository.UserRepository;
import com.es.api.request.GivenAmountRequest;
import com.es.api.service.AmountService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AmountServiceImpl implements AmountService{
	
	
	@Autowired
	private GivenAmountRepository givenAmountRepo;
	@Autowired
	private DigitalServicesRepository digitalServicesRepo;
	@Autowired
	private UserRepository userRepo;
	
	

	@Override
	public String create(GivenAmountRequest givenAmountRequest) {
		Optional<User> optionalUser = userRepo.findById(givenAmountRequest.getUserId());
		if(!optionalUser.isPresent()) {
			 throw new BadRequestException("User Not Found!");
		}
		Optional<DigitalServices> optionalService = digitalServicesRepo.findById(givenAmountRequest.getServiceCodeId());
		
		if(!optionalService.isPresent()) {
			throw new BadRequestException("Service Code Not Found!");
		}
		ObjectMapper objectMapper = new ObjectMapper();
        String countsJson;
        try {
            countsJson = objectMapper.writeValueAsString(givenAmountRequest.getCounts());
        } catch (Exception e) {
        	throw new BadRequestException("");
        }
        GivenAmount givenAmount = GivenAmount.builder()
        		.serviceCode(optionalService.get())
        		.countsJson(countsJson)
        		.createdBy(optionalUser.get())
        		.createdDate(new Date())
        		.build();
		return givenAmountRepo.save(givenAmount).getServiceCode().getServiceName();
	}
	
	
	

}
