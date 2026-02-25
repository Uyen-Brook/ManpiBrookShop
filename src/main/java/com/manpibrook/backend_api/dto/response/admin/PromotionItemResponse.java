package com.manpibrook.backend_api.dto.response.admin;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PromotionItemResponse {
	private Long id;
	private int quantity;
	private Long promotionId;
	private Long productVariantId;
}
