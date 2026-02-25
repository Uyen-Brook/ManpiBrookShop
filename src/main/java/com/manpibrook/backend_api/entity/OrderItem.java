package com.manpibrook.backend_api.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter @Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_item_id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_ghn_id", nullable = false) 
    private OrderGHN order;

    @Column(name = "product_variant_id")
    private Long productVariantId;
    
    private Integer quantity;
    private String productName;
    private BigDecimal price;
    @Column(name = "promotion_id")
    private Long promotionId;
}