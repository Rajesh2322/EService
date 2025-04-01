package com.es.api.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@Table (name="E_SERVICES")
public class DigitalServices {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	private String serviceCode;
	private String serviceName;
	private String desc;
	private String createdBy;
	private Date createdDate;
	
	
	
	  public DigitalServices(Long id, String serviceCode, String serviceName,String desc, String createdBy, Date createdDate) {
	        this.Id = id;
	        this.serviceCode = serviceCode;
	        this.serviceName = serviceName;
	        this.desc = desc;
	        this.createdBy = createdBy;
	        this.createdDate = createdDate;
	    }
	
	
	
}
