package com.manpibrook.backend_api.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manpibrook.backend_api.dto.request.user.CartItemRequest;
import com.manpibrook.backend_api.service.user.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // thêm sản phẩm vào giỏ: cần customer đã đăng nhập (tạm thời truyền customerId, sau này lấy từ JWT)
    @PostMapping("/add/{customerId}")
    public ResponseEntity<?> addToCart(
            @PathVariable Long customerId,
            @Valid @RequestBody CartItemRequest request) {

        cartService.addItem(customerId, request);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Đã thêm sản phẩm vào giỏ hàng"
        ));
    }

    // cập nhật số lượng trong giỏ
    @PatchMapping("/update/{customerId}")
    public ResponseEntity<?> updateQuantity(
            @PathVariable Long customerId,
            @Valid @RequestBody CartItemRequest request) {

        cartService.updateQuantity(customerId, request);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Cập nhật số lượng giỏ hàng thành công"
        ));
    }

    // xóa sản phẩm khỏi giỏ (theo productId / productVariantId)
    @DeleteMapping("/remove/{customerId}/{productId}")
    public ResponseEntity<?> removeItem(
            @PathVariable Long customerId,
            @PathVariable Long productId) {

        cartService.removeItem(customerId, productId);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Đã xóa sản phẩm khỏi giỏ hàng"
        ));
    }
}