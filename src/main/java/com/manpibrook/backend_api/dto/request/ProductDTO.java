package com.manpibrook.backend_api.dto.request;

import lombok.Data;

@Data
public class ProductDTO {
    private Long productId;
    private String productName;
    private String slug;
    private String description;
    private String thumbnail;
    private Double basePrice;
    private Long brandId;
    private Long categoryId;
    private Long supplierId;
    private List<ProductVariantDTOs> variants;
}