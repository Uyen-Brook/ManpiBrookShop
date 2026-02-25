package com.manpibrook.backend_api.controller.admin;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.manpibrook.backend_api.dto.request.admin.ShopInforRequest;
import com.manpibrook.backend_api.dto.response.ShopInforResponse;
import com.manpibrook.backend_api.service.admin.ShopInforService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/shop-infor")
@RequiredArgsConstructor
public class ShopInforController {

    private final ShopInforService shopInforService;

    @GetMapping("/")
    public ResponseEntity<List<ShopInforResponse>> getAll() {
        return ResponseEntity.ok(shopInforService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopInforResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(shopInforService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ShopInforResponse> create(@RequestBody ShopInforRequest request) {
        return ResponseEntity.ok(shopInforService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShopInforResponse> update(@PathVariable Long id,
                                                    @RequestBody ShopInforRequest request) {
        return ResponseEntity.ok(shopInforService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        shopInforService.delete(id);
        return ResponseEntity.noContent().build();
    }
}