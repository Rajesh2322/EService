package com.es.api.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table (name="E_GIVEN_AMOUNT")
public class GivenAmount {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToOne
    private DigitalServices serviceCode;
    private String countsJson;
    private BigDecimal given1Total;
    private BigDecimal given2Total;
    private Date createdDate;
    @ManyToOne
    private User createdBy;

}
