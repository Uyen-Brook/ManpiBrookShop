package com.manpibrook.backend_api.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryResponse {
    private Integer categoryId;
    private String name;
    private String slug;
    private String icon;
}
