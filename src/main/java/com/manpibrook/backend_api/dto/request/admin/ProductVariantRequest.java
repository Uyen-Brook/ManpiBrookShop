package com.manpibrook.backend_api.dto.request.admin;

// VariantRequest.java
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class ProductVariantRequest {

    private BigDecimal price;
    private String sku;
    private Integer stockQuantity;

    // frontend có thể gửi object hoặc json string
    private Object attribute;   // nhận cả Map hoặc String

    private Object imageList;   // nhận cả List hoặc JSON string

    private Long promotionId;

    private static final ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public Map<String, Object> getAttributeAsMap() {

        if (attribute == null) return null;

        if (attribute instanceof Map) {
            return (Map<String, Object>) attribute;
        }

        if (attribute instanceof String str && !str.isBlank()) {
            try {
                return mapper.readValue(str, new TypeReference<>() {});
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid attribute JSON format");
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public List<String> getImageListAsList() {

        if (imageList == null) return null;

        if (imageList instanceof List) {
            return (List<String>) imageList;
        }

        if (imageList instanceof String str && !str.isBlank()) {
            try {
                return mapper.readValue(str, new TypeReference<>() {});
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid imageList JSON format");
            }
        }

        return null;
    }
}