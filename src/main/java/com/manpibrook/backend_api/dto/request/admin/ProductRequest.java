package com.manpibrook.backend_api.dto.request.admin;

// ProductRequest.java
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.constraints.NotBlank;

@Data
public class ProductRequest {

    private String productName;
	
    private String slug;
	
    private String description;
    private String thumbnail;
    
    private Map<String, Object> specification;      
   
    private BigDecimal basePrice;
    private Integer categoryId;
    private Integer brandId;
    private Integer supplierId;
    
    private List<VariantRequest> variants;
}