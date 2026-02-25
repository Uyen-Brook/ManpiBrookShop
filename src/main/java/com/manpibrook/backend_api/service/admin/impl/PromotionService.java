package com.manpibrook.backend_api.service.admin.impl;

//PromotionService.java
import java.util.List;
import com.manpibrook.backend_api.dto.request.admin.PromotionItemRequest;
import com.manpibrook.backend_api.dto.request.admin.PromotionRequest;
import com.manpibrook.backend_api.dto.response.admin.PromotionItemResponse;
import com.manpibrook.backend_api.dto.response.admin.PromotionResponse;

public interface PromotionService {
 // Promotion
 List<PromotionResponse> getAll();
 PromotionResponse getById(Long id);
 PromotionResponse create(PromotionRequest request);
 PromotionResponse update(Long id, PromotionRequest request);
 void delete(Long id);

 // PromotionItem
 List<PromotionItemResponse> getItemsByPromotion(Long promotionId);
 PromotionItemResponse addItem(Long promotionId, PromotionItemRequest request);
 PromotionItemResponse updateItem(Long itemId, PromotionItemRequest request);
 void deleteItem(Long itemId);
}