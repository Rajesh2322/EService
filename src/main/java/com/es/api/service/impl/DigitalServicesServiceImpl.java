package com.es.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.api.entity.DigitalServices;
import com.es.api.repository.DigitalServicesRepository;
import com.es.api.request.DigitalServicesRequest;
import com.es.api.service.DigitalServicesService;

@Service
public class DigitalServicesServiceImpl implements DigitalServicesService{
	
	@Autowired
	private DigitalServicesRepository digitalServicesRepo;
	
	
	@Override
	public String createDigitalService(DigitalServicesRequest request) {
		
		DigitalServices digitalService  = DigitalServices.builder()
				.serviceCode(GenerateServiceCode())
				.serviceName(request.getServiceName())
				.createdBy(request.getCreatedBy())
				.createdDate(new Date())
				.build();
		return digitalServicesRepo.save(digitalService).getServiceCode()+ "_" + digitalService.getServiceName();
	}


	private String GenerateServiceCode() {
		Random random = new Random();
        int randomNum = random.nextInt(1000);
		return "ES-" + String.format("%03d", randomNum);
	}


	@Override
	public List<DigitalServices> getDigitalServicesList() {
		return digitalServicesRepo.findAll();
	}


	
}
