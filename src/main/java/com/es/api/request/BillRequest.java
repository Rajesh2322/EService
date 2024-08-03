package com.es.api.request;

import java.util.Date;

import javax.persistence.OneToMany;

import com.es.api.entity.User;

import lombok.Data;

@Data
public class BillRequest {
	
	private String employeeName;
	private Long serviceCode;
	private Date dor;
	private Integer amount;
	private Integer userCharges;
	private Integer totalAmount;
	private Integer givenAmountByCustomer;
	private Integer refundAmountToCustomer;
	private String cashLessTransaction;
	private Integer custAmountPendingForUs;
	private Long userId;

}
