package com.manpibrook.backend_api.dto.request.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class ProductRequest {

    private String productName;
    private String slug;
    private String description;

    private BigDecimal basePrice;

    private Integer categoryId;
    private Integer brandId;
    private Integer supplierId;

    private Object specification; // nhận cả Map hoặc JSON string

    private List<ProductVariantRequest> variants;

    private static final ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public Map<String, Object> getSpecificationAsMap() {

        if (specification == null) return null;

        if (specification instanceof Map) {
            return (Map<String, Object>) specification;
        }

        if (specification instanceof String str && !str.isBlank()) {
            try {
                return mapper.readValue(str, new TypeReference<>() {});
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid specification JSON format");
            }
        }

        return null;
    }
}