package com.manpibrook.backend_api.entity.enums;

import lombok.Getter;

@Getter
public enum Role {
	ADMIN("Quản trị viên"),
	STAFF("Nhân viên"),
	CUSTOMMER("khách hàng");
	private final String description;
	Role(String description) {
		this.description = description;
	}
}
