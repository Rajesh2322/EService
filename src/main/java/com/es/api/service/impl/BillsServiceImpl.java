package com.es.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.api.entity.Bills;
import com.es.api.entity.User;
import com.es.api.exception.BadRequestException;
import com.es.api.repository.BillsRepository;
import com.es.api.repository.UserRepository;
import com.es.api.request.BillRequest;
import com.es.api.service.BillsService;

@Service
public class BillsServiceImpl implements BillsService{
	
	@Autowired
	private BillsRepository billRepo;
	
	@Autowired
	private UserRepository userRepo;
	

	@Override
	public Long create(BillRequest billRequest) {
		Optional<User> optionalUser = userRepo.findById(billRequest.getUserId());
		if(optionalUser.isEmpty()) {
				 throw new BadRequestException("User Not Found !");
		}
		Bills billDetails = Bills.builder()
				.id(generateBillId())
				.employeeName(billRequest.getEmployeeName())
				.serviceName(billRequest.getServiceName())
				.dor(billRequest.getDor())
				.amount(billRequest.getAmount())
				.userCharges(billRequest.getUserCharges())
				.totalAmount(billRequest.getTotalAmount())
				.givenAmountByCustomer(billRequest.getGivenAmountByCustomer())
				.refundAmountToCustomer(billRequest.getRefundAmountToCustomer())
				.cashLessTransaction(billRequest.getCashLessTransaction())
				.custAmountPendingForUs(billRequest.getCustAmountPendingForUs())
				.userId(optionalUser.get())
				.createdDate(new Date())
				.build();
		
		return billRepo.save(billDetails).getId();
	}

	private Long generateBillId() {
		return (long) ThreadLocalRandom.current().nextInt(1000, 10000);
	}

	@Override
	public List<Bills> findAllByUserId(Long userId) {
		if(userId == 0) {
			return billRepo.findAllByOrderByCreatedDateDesc();
		}else {
		return billRepo.findByUserIdIdOrderByCreatedDateDesc(userId);
		}
	}

}
