package com.manpibrook.backend_api.dto.request.admin;

// VariantRequest.java
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

@Data
public class VariantRequest {
    private BigDecimal price;
    private String sku;
    private Integer stockQuantity;
    
    private Map<String, Object> attribute;   
    private List<String> imageList;     
    
    private Long promotionId;
}