package com.manpibrook.backend_api.entity;

import jakarta.persistence.Column;

public class ShopInfor {
	@Column(name = "from_name", nullable = false)
	private String fromName;

	@Column(name = "to_phone", nullable = false)
	private String toPhone;

	@Column(name = "to_address", nullable = false)
	private String toAddress;

	@Column(name = "to_ward_code") // Mã xã/phường của GHN
	private String toWardCode;

	@Column(name = "to_province_code") // Mã quận/huyện của GHN
	private Integer toProvinceCode;

}
