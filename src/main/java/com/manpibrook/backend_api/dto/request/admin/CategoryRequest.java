package com.manpibrook.backend_api.dto.request.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
	@NotBlank(message = "Tên không được bỏ trống")
    private String name;
	@NotBlank(message = " slug không được bỏ trống")
    private String slug;
    private String icon;
}
