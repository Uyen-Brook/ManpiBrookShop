package com.manpibrook.backend_api.dto.response.admin;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO cho ProductVariant.
 * Chứa thông tin biến thể sản phẩm: giá, SKU, tồn kho, thuộc tính, danh sách ảnh...
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariantResponse {
    private Long productVariantId;
    
    private BigDecimal price;
    
    private String sku;
    
    private Integer stockQuantity;
    
    private Integer weight;
    
    /**
     * Thuộc tính biến thể dưới dạng Map (vd: {"color": "red", "size": "XL"}).
     * Database lưu dưới dạng JSON string, converter tự động parse thành Map.
     */
    private Map<String, Object> attribute;
    
    /**
     * Danh sách đường dẫn ảnh của variant.
     * Database lưu dưới dạng JSON array string, converter tự động parse thành List.
     */
    private List<String> imageList;
    
    private Long promotionId;
}