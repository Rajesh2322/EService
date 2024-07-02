package com.es.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.es.api.entity.DigitalServices;
import com.es.api.request.DigitalServicesRequest;
import com.es.api.response.ServiceResponse;
import com.es.api.service.DigitalServicesService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/digitalServices")
public class DigitalServicesController {
	
	@Autowired
	private DigitalServicesService digitalServicesService;
	

	@PostMapping("/create")
	public ResponseEntity<?> createDigitalService(@RequestBody DigitalServicesRequest request){
	    log.info("Create New Digital Service - start");
	    log.info("Received Digital Service Request: {}", request);

	   String serviceCode = digitalServicesService.createDigitalService(request);
	    log.info("Created Digital Service: {}", serviceCode);

	    if (serviceCode == null) {
	        log.error("digitalServicesService.create returned null");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("digitalServicesService.create returned null");
	    }

	    return ResponseEntity.ok().body(new ResponseEntity<>(ServiceResponse.builder()
	        .data(serviceCode)
	        .msg("Dogital Service created Successfully !")
	        .build(), HttpStatus.CREATED));
	}
	
	
	 @GetMapping("/all")
	    public ResponseEntity<List<DigitalServices>> getDigitalServicesList() {
	        log.info("Get All Digital Services List - start");

	        List<DigitalServices> digitalServicesList = digitalServicesService.getDigitalServicesList();

	        log.info("Retrieved digital Services list: {}", digitalServicesList);
	        return ResponseEntity.ok().body(digitalServicesList);
	    }
}
