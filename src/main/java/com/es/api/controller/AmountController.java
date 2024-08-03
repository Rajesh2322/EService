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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.es.api.entity.Bills;
import com.es.api.request.GivenAmountRequest;
import com.es.api.request.GroupedBillsRequest;
import com.es.api.response.BillsGroupedByService;
import com.es.api.response.ServiceResponse;
import com.es.api.service.AmountService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/amount")
public class AmountController {
	
	@Autowired
	private AmountService amountService;
	
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody GivenAmountRequest givenAmountRequest){
	    log.info("Create GivenAmount - start");
	    log.info("Received GivenAmountRequest: {}", givenAmountRequest);

	   String ServiceCode = amountService.create(givenAmountRequest);

	    if (ServiceCode == null) {
	        log.error("amountService.create returned null");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("amountService.create returned null");
	    }

	    return ResponseEntity.ok().body(new ResponseEntity<>(ServiceResponse.builder()
	        .data(ServiceCode)
	        .msg("Created Successfully !")
	        .build(), HttpStatus.CREATED));
	}

	@PostMapping("/getFinalAmountById")
	 public ResponseEntity<List<BillsGroupedByService>> getBillsGroupedByServiceName(
	            @RequestBody GroupedBillsRequest request) {
	        List<BillsGroupedByService> billsList = amountService.findBillsGroupedByServiceName(request);
	        return ResponseEntity.ok().body(billsList);
	    }

}
