package com.es.api.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class BillsGroupedByService {

	private String serviceName;
	private BigDecimal billAmount;
	private BigDecimal userCharges;
	private BigDecimal totalAmount;
	private BigDecimal openCounter;
	private BigDecimal openNet;
	private BigDecimal topup;
	private BigDecimal closingCount;
	private BigDecimal cashLess;
	private BigDecimal given1;
	private BigDecimal given2;
	private BigDecimal givenTotal;
	private BigDecimal pendingAmount;
	private BigDecimal total;
	

    public BillsGroupedByService(String serviceName, BigDecimal billAmount, BigDecimal userCharges, BigDecimal totalAmount, 
                                 BigDecimal openCounter, BigDecimal openNet, BigDecimal topup, BigDecimal closingCount, 
                                 BigDecimal cashLess,BigDecimal given1, BigDecimal given2,BigDecimal givenTotal, BigDecimal pendingAmount, BigDecimal total) {
        this.serviceName = serviceName;
        this.billAmount = billAmount;
        this.userCharges = userCharges;
        this.totalAmount = totalAmount;
        this.openCounter = openCounter;
        this.openNet = openNet;
        this.topup = topup;
        this.closingCount = closingCount;
        this.cashLess = cashLess;
        this.given1 = given1;
        this.given2 = given2;
        this.givenTotal = givenTotal;
        this.pendingAmount = pendingAmount;
        this.total = total;
    }

}
