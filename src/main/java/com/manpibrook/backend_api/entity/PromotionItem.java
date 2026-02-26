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
@Table(name = "promotion_item")
@Getter @Setter
public class PromotionItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	private int quanlity;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
	private ProductVariant productVariant;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", nullable = false) 
	private Promotion promotion;
	
	@Column(name = "is_gift")
	private Boolean isGift = false;

	@Column(name = "original_price")
	private BigDecimal originalPrice;
}
