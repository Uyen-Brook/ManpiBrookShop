package com.manpibrook.backend_api.dto.response.user;

import lombok.Data;

@Data
public class ProductResponse {
    private Long productId;
    private String productName;
    private String slug;
    private Double basePrice;
    private String categoryName; // Chỉ lấy tên để trả về cho Client
    private String brandName;
}