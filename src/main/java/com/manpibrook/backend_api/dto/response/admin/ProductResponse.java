package com.manpibrook.backend_api.dto.response.admin;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String productName;
    private String slug;
    private String thumbnail;
    private BigDecimal basePrice;
    private String categoryName;
    private String brandName;
    private List<VariantResponse> variants;
}