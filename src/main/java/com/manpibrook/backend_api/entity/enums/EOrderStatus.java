package com.manpibrook.backend_api.entity.enums;

import lombok.Getter;

@Getter
public enum EOrderStatus {
	PENDDING("Chờ thanh toán"),
	PAID("Đã thanh toán"),
	CONFIRMED("Đã xác nhận"),
	SHIPPING("Đang giao hàng"),
	DELIVERED("Đã giao hàng"),
	CANCELLED("Đã hủy");
	
	private final String description;
	EOrderStatus(String description) {
		this.description = description;
	}
}
