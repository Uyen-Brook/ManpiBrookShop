package com.manpibrook.backend_api.entity.enums;

import lombok.Getter;

@Getter
public enum ERole {
	ADMIN("Quản trị viên"),
	STAFF("Nhân viên"),
	CUSTOMMER("khách hàng");
	private final String description;
	ERole(String description) {
		this.description = description;
	}
}
