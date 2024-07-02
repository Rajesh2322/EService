package com.es.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.es.api.entity.Bills;
import com.es.api.entity.User;
import com.es.api.request.BillRequest;
import com.es.api.response.ServiceResponse;
import com.es.api.service.BillsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/bills")
public class BillsController {
	
	
	@Autowired
	private BillsService billsService;
	
	
	@PostMapping("/create")
	public ResponseEntity<?> createUser(@RequestBody BillRequest billRequest){
	    log.info("Create New Bill - start");
	    log.info("Received BillRequest: {}", billRequest);

	   Long billId = billsService.create(billRequest);
	    log.info("Created bill: {}", billId);

	    if (billId == null) {
	        log.error("billsService.create returned null");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("billsService.create returned null");
	    }

	    return ResponseEntity.ok().body(new ResponseEntity<>(ServiceResponse.builder()
	        .data(billId.toString())
	        .msg("Bill created Successfully !")
	        .build(), HttpStatus.CREATED));
	}

	
	 @GetMapping("/getBillsById")
	    public ResponseEntity<List<Bills>> getBillsById(Long userId) {
	        log.info("Get User by ID service - start");
	        log.info("Received User Name: {}", userId);

	        List<Bills> billsList = billsService.findAllByUserId(userId);

	        log.info("Retrieved bills list: {}", billsList);
	        return ResponseEntity.ok().body(billsList);
	    }
}
