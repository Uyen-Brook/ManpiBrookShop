package com.manpibrook.backend_api.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "promotion_item")
@Getter @Setter
public class PromotionItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="promotion_item_id")
	private long promotionItemId;
	@Column(name = "promotion_id")
	private Long promotionId;
	@Column(name = "product_variant_id")
	private Long productVariantId;
	private int quanlity;
}
