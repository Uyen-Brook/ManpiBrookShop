package com.manpibrook.backend_api.dto.response.admin;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

@Data
class VariantResponse{
    private BigDecimal price;
    private String sku;
    private Integer stockQuantity;
    private JsonNode attribute;
    private JsonNode imageList;
}