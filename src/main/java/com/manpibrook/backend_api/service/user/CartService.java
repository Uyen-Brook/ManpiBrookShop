package com.manpibrook.backend_api.service.user;

import org.springframework.stereotype.Service;

import com.manpibrook.backend_api.dto.request.user.CartItemRequest;
import com.manpibrook.backend_api.entity.Cart;
import com.manpibrook.backend_api.entity.CartItem;
import com.manpibrook.backend_api.entity.ProductVariant;
import com.manpibrook.backend_api.repository.CartItemRepository;
import com.manpibrook.backend_api.repository.CartRepository;
import com.manpibrook.backend_api.repository.ProductVariantRepository;
import com.manpibrook.backend_api.utils.BusinessException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;

    private Cart getOrCreateCart(Long customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setCustomerId(customerId);
                    return cartRepository.save(cart);
                });
    }

    public void addItem(Long customerId, CartItemRequest request) {
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new BusinessException("Số lượng phải lớn hơn 0");
        }

        ProductVariant variant = productVariantRepository.findById(request.getProductVariantId())
                .orElseThrow(() -> new BusinessException("Không tìm thấy sản phẩm"));

        if (variant.getStockQuantity() != null && request.getQuantity() > variant.getStockQuantity()) {
            throw new BusinessException("Số lượng vượt quá tồn kho");
        }

        Cart cart = getOrCreateCart(customerId);

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), request.getProductVariantId())
                .orElse(null);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCartId(cart.getId());
            cartItem.setProductId(request.getProductVariantId());
            cartItem.setQuantity(request.getQuantity());
        } else {
            int newQuantity = cartItem.getQuantity() + request.getQuantity();
            if (variant.getStockQuantity() != null && newQuantity > variant.getStockQuantity()) {
                throw new BusinessException("Số lượng vượt quá tồn kho");
            }
            cartItem.setQuantity(newQuantity);
        }

        cartItemRepository.save(cartItem);
    }

    public void updateQuantity(Long customerId, CartItemRequest request) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new BusinessException("Giỏ hàng không tồn tại"));

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), request.getProductVariantId())
                .orElseThrow(() -> new BusinessException("Sản phẩm không có trong giỏ hàng"));

        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            // nếu số lượng <= 0 thì coi như xóa khỏi giỏ
            cartItemRepository.delete(cartItem);
            return;
        }

        ProductVariant variant = productVariantRepository.findById(request.getProductVariantId())
                .orElseThrow(() -> new BusinessException("Không tìm thấy sản phẩm"));

        if (variant.getStockQuantity() != null && request.getQuantity() > variant.getStockQuantity()) {
            throw new BusinessException("Số lượng vượt quá tồn kho");
        }

        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
    }

    public void removeItem(Long customerId, Long productId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new BusinessException("Giỏ hàng không tồn tại"));

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new BusinessException("Sản phẩm không có trong giỏ hàng"));

        cartItemRepository.delete(cartItem);
    }
}
