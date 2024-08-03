package com.es.api.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.es.api.entity.Bills;
import com.es.api.entity.DigitalServices;
import com.es.api.entity.GivenAmount;
import com.es.api.entity.User;
import com.es.api.exception.BadRequestException;
import com.es.api.repository.BillsRepository;
import com.es.api.repository.DigitalServicesRepository;
import com.es.api.repository.GivenAmountRepository;
import com.es.api.repository.UserRepository;
import com.es.api.request.GivenAmountRequest;
import com.es.api.request.GroupedBillsRequest;
import com.es.api.response.BillsGroupedByService;
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
	@Autowired
	private BillsRepository billRepo;
	
	

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
        		.given1Total(givenAmountRequest.getGiven1Total())
        		.given2Total(givenAmountRequest.getGiven2Total())
        		.createdBy(optionalUser.get())
        		.createdDate(new Date())
        		.build();
		return givenAmountRepo.save(givenAmount).getServiceCode().getServiceName();
	}




	@Transactional(readOnly = true)
	public List<BillsGroupedByService> findBillsGroupedByServiceName(GroupedBillsRequest request) {
		List<Bills> bills = null;
		 List<GivenAmount> givenAmount = null;
		 LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
	        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
	        Date startDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
	        Date endDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
	        
		if(request.getUserId() == 0) {
			 bills = billRepo.findByServiceNameIn(request.getServiceNames());
			 givenAmount = givenAmountRepo.findByCreatedDateBetween(startDate,endDate );
		}else {
		
		 bills = billRepo.findByServiceNameInAndUserIdId(request.getServiceNames(), request.getUserId());
		 givenAmount = givenAmountRepo.findByCreatedByIdAndCreatedDateBetween(request.getUserId(), startDate,endDate);
		}
		
		
		 Map<String, BigDecimal[]> givenTotalsMap = new HashMap<>();
	        for (GivenAmount ga : givenAmount) {
	            String serviceName = ga.getServiceCode().getServiceName();
	            givenTotalsMap.computeIfAbsent(serviceName, k -> new BigDecimal[] { BigDecimal.ZERO, BigDecimal.ZERO });
	            givenTotalsMap.get(serviceName)[0] = givenTotalsMap.get(serviceName)[0].add(ga.getGiven1Total() != null ? ga.getGiven1Total() : BigDecimal.ZERO);
	            givenTotalsMap.get(serviceName)[1] = givenTotalsMap.get(serviceName)[1].add(ga.getGiven2Total() != null ? ga.getGiven2Total() : BigDecimal.ZERO);
	        }
		
		 return bills.stream()
	                .collect(Collectors.groupingBy(Bills::getServiceName))
	                .entrySet()
	                .stream()
	                .map(entry -> {
	                    String serviceName = entry.getKey();
	                    List<Bills> list = entry.getValue();

	                    BigDecimal billAmount = list.stream()
	                            .map(Bills::getAmount)
	                            .map(BigDecimal::valueOf)
	                            .reduce(BigDecimal.ZERO, BigDecimal::add);
	                    BigDecimal userCharges = list.stream()
	                            .map(Bills::getUserCharges)
	                            .map(BigDecimal::valueOf)
	                            .reduce(BigDecimal.ZERO, BigDecimal::add);
	                    BigDecimal totalAmount = billAmount.add(userCharges);
	                    BigDecimal openCounter = BigDecimal.ZERO;
	                    BigDecimal openNet = BigDecimal.ZERO; // Assuming openNet and topup are calculated similarly or are zero
	                    BigDecimal topup = BigDecimal.ZERO;
	                    BigDecimal closingCount = BigDecimal.ZERO; // Assuming closingCount is calculated similarly or is zero
	                    BigDecimal cashLess = BigDecimal.ZERO; // Assuming cashLess is calculated similarly or is zero
	                    BigDecimal pendingAmount = BigDecimal.ZERO; // Assuming pendingAmount is calculated similarly or is zero
	                    BigDecimal total = billAmount.add(userCharges).add(openCounter).add(openNet)
	                            .add(topup).add(closingCount).add(cashLess).add(pendingAmount);
	                    BigDecimal given1 = givenTotalsMap.getOrDefault(serviceName, new BigDecimal[] { BigDecimal.ZERO, BigDecimal.ZERO })[0];
	                    BigDecimal given2 = givenTotalsMap.getOrDefault(serviceName, new BigDecimal[] { BigDecimal.ZERO, BigDecimal.ZERO })[1];
	                    BigDecimal givenTotal =  given1.add(given2);;

	                    return new BillsGroupedByService(serviceName, billAmount, userCharges, totalAmount, 
	                                                      openCounter, openNet, topup, closingCount, cashLess,given1, given2, givenTotal,pendingAmount, total);
	                })
	                .collect(Collectors.toList());
    }
	
	
	

}
