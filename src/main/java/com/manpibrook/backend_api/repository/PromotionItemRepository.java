package com.manpibrook.backend_api.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manpibrook.backend_api.dto.response.admin.PromotionResponse;
import com.manpibrook.backend_api.entity.PromotionItem;

public interface PromotionItemRepository extends JpaRepository<PromotionItem, Long>{

	Collection<PromotionResponse> findByPromotion_PromotionId(Long promotionId);

}
