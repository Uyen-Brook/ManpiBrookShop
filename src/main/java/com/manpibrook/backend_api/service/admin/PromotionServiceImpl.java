package com.manpibrook.backend_api.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manpibrook.backend_api.dto.request.admin.PromotionItemRequest;
import com.manpibrook.backend_api.dto.request.admin.PromotionRequest;
import com.manpibrook.backend_api.dto.response.admin.PromotionItemResponse;
import com.manpibrook.backend_api.dto.response.admin.PromotionResponse;
import com.manpibrook.backend_api.entity.ProductVariant;
import com.manpibrook.backend_api.entity.Promotion;
import com.manpibrook.backend_api.entity.PromotionItem;
import com.manpibrook.backend_api.repository.ProductVariantRepository;
import com.manpibrook.backend_api.repository.PromotionItemRepository;
import com.manpibrook.backend_api.repository.PromotionRepository;
import com.manpibrook.backend_api.service.admin.impl.PromotionService;
import com.manpibrook.backend_api.utils.BusinessException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionServiceImpl implements PromotionService {

	private final PromotionRepository promotionRepository;
	private final PromotionItemRepository promotionItemRepository;
	private final ProductVariantRepository productVariantRepository;

	// ==== Promotion ====
	@Override
	public List<PromotionResponse> getAll() {
		return promotionRepository.findAll().stream().map(this::mapToResponse).toList();
	}

	@Override
	public PromotionResponse getById(Long id) {
		Promotion p = promotionRepository.findById(id).orElseThrow(() -> new BusinessException("Promotion not found"));
		return mapToResponse(p);
	}

	@Override
	public PromotionResponse create(PromotionRequest request) {
		Promotion p = buildFromRequest(new Promotion(), request);
		return mapToResponse(promotionRepository.save(p));
	}

	@Override
	public PromotionResponse update(Long id, PromotionRequest request) {
		Promotion p = promotionRepository.findById(id).orElseThrow(() -> new BusinessException("Promotion not found"));
		return mapToResponse(promotionRepository.save(buildFromRequest(p, request)));
	}

	@Override
	public void delete(Long id) {
		if (!promotionRepository.existsById(id)) {
			throw new BusinessException("Promotion not found");
		}
		promotionRepository.deleteById(id);
	}

	@Override
	public List<PromotionItemResponse> getItemsByPromotion(Long promotionId) {
		return promotionItemRepository.findByPromotion_PromotionId(promotionId).stream().map(this::mapItemToResponse)
				.toList();
	}

	@Override
	public PromotionItemResponse addItem(Long promotionId, PromotionItemRequest request) {
		Promotion promotion = promotionRepository.findById(promotionId)
				.orElseThrow(() -> new BusinessException("Promotion not found"));
		ProductVariant variant = productVariantRepository.findById(request.getProductVariantId())
				.orElseThrow(() -> new BusinessException("ProductVariant not found"));

		PromotionItem item = new PromotionItem();
		item.setPromotion(promotion);
		item.setProductVariant(variant);
		item.setQuanlity(request.getQuantity());

		return mapItemToResponse(promotionItemRepository.save(item));
	}

	@Override
	public PromotionItemResponse updateItem(Long itemId, PromotionItemRequest request) {
		PromotionItem item = promotionItemRepository.findById(itemId)
				.orElseThrow(() -> new BusinessException("PromotionItem not found"));
		item.setQuanlity(request.getQuantity());
		return mapItemToResponse(promotionItemRepository.save(item));
	}

	@Override
	public void deleteItem(Long itemId) {
		promotionItemRepository.deleteById(itemId);
	}

	private Promotion buildFromRequest(Promotion p, PromotionRequest r) {
		if (r.getName() != null && !r.getName().isBlank()) {
			p.setName(r.getName());
		}
		if (r.getDiscountPercent() != null) {
			p.setDiscountPercent(r.getDiscountPercent());
		}
		if (r.getDiscountAmount() != null) {
			p.setDiscountAmount(r.getDiscountAmount());
		}
		if (r.getGiftDescription() != null && !r.getGiftDescription().isBlank()) {
			p.setGiftDescription(r.getGiftDescription());
		}
		if (r.getGiftType() != null) {
			p.setGiftType(r.getGiftType());
		}
		if (r.getGiftProductId() != null) {
			p.setGiftProductId(r.getGiftProductId());
		}
		if (r.getStartDate() != null) {
			p.setStartDate(r.getStartDate());
		}
		if (r.getEndDate() != null) {
			p.setEndDate(r.getEndDate());
		}
		return p;
	}

	private PromotionResponse mapToResponse(Promotion p) {
		PromotionResponse res = new PromotionResponse();
		res.setPromotionId(p.getPromotionId());
		res.setName(p.getName());
		res.setDiscountPercent(p.getDiscountPercent());
		res.setDiscountAmount(p.getDiscountAmount());
		res.setGiftDescription(p.getGiftDescription());
		res.setGiftType(p.getGiftType());
		res.setGiftProductId(p.getGiftProductId());
		res.setStartDate(p.getStartDate());
		res.setEndDate(p.getEndDate());
		return res;
	}

	private PromotionItemResponse mapItemToResponse(PromotionItem item) {
		PromotionItemResponse res = new PromotionItemResponse();
		res.setId(item.getId());
		res.setQuantity(item.getQuanlity());
		res.setPromotionId(item.getPromotion().getPromotionId());
		res.setProductVariantId(item.getProductVariant().getProductVariantId());
		return res;
	}
}