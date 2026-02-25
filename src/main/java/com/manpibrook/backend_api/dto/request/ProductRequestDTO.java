package com.manpibrook.backend_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String productName;
    private String description;
    private Double basePrice;
    private Long categoryId;
    private Long brandId;
}