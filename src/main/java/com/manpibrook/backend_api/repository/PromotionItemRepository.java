package com.manpibrook.backend_api.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manpibrook.backend_api.entity.PromotionItem;

@Repository
public interface PromotionItemRepository extends JpaRepository<PromotionItem, Long>{

    Collection<PromotionItem> findByPromotion_PromotionId(Long promotionId);

}
