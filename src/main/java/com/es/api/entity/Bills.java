package com.es.api.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name="E_BILLS")
public class Bills {
	
	@Id
	private Long id;
	
	private String employeeName;
	private String serviceName;
	private Date dor;
	private Integer amount;
	private Integer userCharges;
	private Integer totalAmount;
	private Integer givenAmountByCustomer;
	private Integer refundAmountToCustomer;
	private String cashLessTransaction;
	private Integer custAmountPendingForUs;
	private Date createdDate;
	@ManyToOne
	private User userId;
	

}
