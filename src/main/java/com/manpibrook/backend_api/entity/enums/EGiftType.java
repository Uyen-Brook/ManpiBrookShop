
package com.manpibrook.backend_api.entity.enums;

public enum EGiftType {
	INTERNAL("Sản phẩm có bán"),
	EXTERNAL("Sản phẩm không bán");
	private final String description;
	EGiftType(String description) {
		this.description = description;
	}
}
