package com.manpibrook.backend_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class BrandResponse {
	private Integer brandId;
	private String name;
	private String url;
}
