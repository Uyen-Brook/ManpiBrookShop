package com.manpibrook.backend_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CategoryResponse {
	 	private Integer categoryId;
	    private String name;
	    private String slug;
	    private String icon;
}
