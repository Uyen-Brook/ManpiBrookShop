package com.manpibrook.backend_api.dto.request.admin;

import lombok.Data;

@Data
public class CategoryRequest {
 
    private String name;
   
    private String slug;

    private String icon;
}