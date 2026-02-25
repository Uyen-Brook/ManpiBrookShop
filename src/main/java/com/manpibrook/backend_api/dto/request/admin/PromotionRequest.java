package com.manpibrook.backend_api.dto.request.admin;

import java.time.LocalDateTime;

import com.manpibrook.backend_api.entity.enums.EGiftType;

import lombok.Data;

//PromotionRequest.java
@Data
public class PromotionRequest {
	private String name;
	private Double discountPercent;
	private Double discountAmount;
	private String giftDescription;
	private EGiftType giftType; // enum
	private Long giftProductId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
}