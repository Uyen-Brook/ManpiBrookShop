package com.manpibrook.backend_api.dto.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductDetailResponse {
    private Long id;
    private String productName;
    private String slug;
    private String description;
    private String thumbnail;
    private Map<String, Object> specification;
    private Double averageRating;

    private BigDecimal basePrice;
    private String categoryName;
    private String brandName;
    private String supplierName;

    private String warrantyPolicy;
    private String returnPolicy;
    private String shippingPolicy;

    private PromotionInfo promotion;           // thông tin khuyến mãi áp dụng
    private List<ProductVariantResponse> variants; // tất cả biến thể

    @Getter @Setter
    public static class PromotionInfo {
        private Long promotionId;
        private String name;
        private Double discountPercent;
        private Double discountAmount;
        private String giftDescription;
        private String giftType;
    }

    @Getter @Setter
    public static class ProductVariantResponse {
        private Long productVariantId;
        private BigDecimal price;
        private String sku;
        private Integer stockQuantity;
        private Integer weight;
        private Map<String, Object> attribute;   // {color,size...}
        private List<String> imageList;
    }
}