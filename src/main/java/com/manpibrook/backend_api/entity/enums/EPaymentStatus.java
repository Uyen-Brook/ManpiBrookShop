package com.manpibrook.backend_api.entity.enums;

public enum EPaymentStatus {
	PENDING("ĐANG XỬ LÝ"), 
	FAIL("Thất bại"),
	SUCCESS("Thành công");
	private final String description;
	private EPaymentStatus(String description) {
		this.description = description;
	}
}
