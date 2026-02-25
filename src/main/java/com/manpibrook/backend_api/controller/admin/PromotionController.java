package com.manpibrook.backend_api.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manpibrook.backend_api.dto.request.admin.PromotionItemRequest;
import com.manpibrook.backend_api.dto.request.admin.PromotionRequest;
import com.manpibrook.backend_api.dto.response.admin.PromotionItemResponse;
import com.manpibrook.backend_api.dto.response.admin.PromotionResponse;
import com.manpibrook.backend_api.service.admin.impl.PromotionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    // ==== Promotion CRUD ====
    @GetMapping
    public ResponseEntity<List<PromotionResponse>> getAll() {
        return ResponseEntity.ok(promotionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(promotionService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PromotionResponse> create(@RequestBody PromotionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(promotionService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotionResponse> update(@PathVariable Long id,
                                                    @RequestBody PromotionRequest request) {
        return ResponseEntity.ok(promotionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        promotionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ==== PromotionItem CRUD ====
    @GetMapping("/{promotionId}/items")
    public ResponseEntity<List<PromotionItemResponse>> getItems(@PathVariable Long promotionId) {
        return ResponseEntity.ok(promotionService.getItemsByPromotion(promotionId));
    }

    @PostMapping("/{promotionId}/items")
    public ResponseEntity<PromotionItemResponse> addItem(@PathVariable Long promotionId,
                                                         @RequestBody PromotionItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(promotionService.addItem(promotionId, request));
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<PromotionItemResponse> updateItem(@PathVariable Long itemId,
                                                            @RequestBody PromotionItemRequest request) {
        return ResponseEntity.ok(promotionService.updateItem(itemId, request));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        promotionService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
}
