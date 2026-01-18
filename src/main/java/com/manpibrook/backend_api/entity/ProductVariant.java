package com.manpibrook.backend_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tools.jackson.databind.JsonNode;

@Entity
@Table(name = "product_variants")
@Getter @Setter
public class ProductVariant {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "product_variant_id")
    private Long productVariantId;

    @Column(name = "product_id")
    private Long productId;

    private Double price;
    private String sku;
    @Column(name = "stock_quanlity")
    private Integer stockQuantity;
    private JsonNode attribute;
    
    @Column(name = "image_list", columnDefinition = "TEXT")
    private JsonNode imageList;

    @Column(name = "promotion_id")
    private Long promotionId;
}
