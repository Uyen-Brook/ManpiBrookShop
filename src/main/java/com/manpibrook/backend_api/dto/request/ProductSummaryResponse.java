package com.manpibrook.backend_api.dto.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductSummaryResponse {
    private Long id;
    private String productName;
    private String slug;
    private String thumbnail;

    // Giá gốc + giá sau khuyến mãi
    private BigDecimal originalPrice;      // basePrice
    private BigDecimal finalPrice;         // sau khi áp promotion (nếu có)
    private Double discountPercent;        // % giảm, null nếu không có
 
    // Thông tin hiển thị nhanh
    private String warrantyPolicy;         // chính sách bảo hành
    private String shortDescription;       // mô tả ngắn
    private Double averageRating;
}