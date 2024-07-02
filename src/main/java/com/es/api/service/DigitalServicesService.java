package com.es.api.service;

import java.util.List;

import com.es.api.entity.DigitalServices;
import com.es.api.request.DigitalServicesRequest;

public interface DigitalServicesService {
	
	String createDigitalService(DigitalServicesRequest request);

	List<DigitalServices> getDigitalServicesList();



}
