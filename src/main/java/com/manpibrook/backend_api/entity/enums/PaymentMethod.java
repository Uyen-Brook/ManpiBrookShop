package com.manpibrook.backend_api.entity.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
	COD("Thanh toán khi nhận hàng"),
	BANK_TRANSFER("Chuyển tiền qua ngân hàng"), 
	WALLET("thẻ tín dụng");
	
	private final String description;
	private PaymentMethod(String description) {
		this.description = description;
	}
}
