package com.manpibrook.backend_api.dto.request.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionItemRequest {
	private int quantity;
	private Long productVariantId;
}
