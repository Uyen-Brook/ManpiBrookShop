package com.manpibrook.backend_api.service.user;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.manpibrook.backend_api.dto.request.user.CheckoutItemRequest;
import com.manpibrook.backend_api.dto.request.user.CheckoutRequest;
import com.manpibrook.backend_api.dto.response.user.CheckoutResponse;
import com.manpibrook.backend_api.entity.Cart;
import com.manpibrook.backend_api.entity.OrderGHN;
import com.manpibrook.backend_api.entity.OrderItem;
import com.manpibrook.backend_api.entity.PaymentTransaction;
import com.manpibrook.backend_api.entity.ProductVariant;
import com.manpibrook.backend_api.entity.Promotion;
import com.manpibrook.backend_api.entity.PromotionItem;
import com.manpibrook.backend_api.entity.enums.EOrderStatus;
import com.manpibrook.backend_api.entity.enums.EPaymentMethod;
import com.manpibrook.backend_api.entity.enums.EPaymentStatus;
import com.manpibrook.backend_api.repository.CartItemRepository;
import com.manpibrook.backend_api.repository.CartRepository;
import com.manpibrook.backend_api.repository.OrderGHNRepository;
import com.manpibrook.backend_api.repository.OrderItemRepository;
import com.manpibrook.backend_api.repository.PaymentTransactionRepository;
import com.manpibrook.backend_api.repository.ProductVariantRepository;
import com.manpibrook.backend_api.repository.PromotionItemRepository;
import com.manpibrook.backend_api.repository.PromotionRepository;
import com.manpibrook.backend_api.utils.BusinessException;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderGHNRepository orderGHNRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final ProductVariantRepository productVariantRepository;
    private final PromotionRepository promotionRepository;
    private final PromotionItemRepository promotionItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final VnPayService vnPayService;

    /**
     * Checkout + tính khuyến mãi.
     *
     * - Chọn 1..n sản phẩm từ giỏ hàng.
     * - Nếu COD: tạo đơn, xóa item khỏi giỏ, không gọi VNPAY.
     * - Nếu BANK_TRANSFER (VNPAY): tạo đơn, xóa item khỏi giỏ, tạo PaymentTransaction và URL VNPAY.
     */
    public CheckoutResponse checkoutWithVnPay(Long customerId, CheckoutRequest request) {

        validateCheckoutRequest(request);

        OrderGHN order = buildOrder(request);
        order = orderGHNRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;
        Set<Long> appliedPromotionIds = new HashSet<>();

        List<CheckoutItemRequest> items = request.getItems();
        for (CheckoutItemRequest itemReq : items) {

            ProductVariant variant = productVariantRepository.findById(itemReq.getProductVariantId())
                    .orElseThrow(() -> new BusinessException("Không tìm thấy sản phẩm"));

            validateStock(variant, itemReq.getQuantity());

            Long promotionId = variant.getPromotionId();
            Promotion promotion = null;
            if (promotionId != null) {
                promotion = promotionRepository.findById(promotionId).orElse(null);
            }

            BigDecimal unitPrice = variant.getPrice();
            if (promotion != null) {
                unitPrice = applyDiscount(unitPrice, promotion);
                appliedPromotionIds.add(promotion.getPromotionId());
            }

            BigDecimal lineAmount = unitPrice.multiply(BigDecimal.valueOf(itemReq.getQuantity()));

            OrderItem orderItem = buildOrderItem(order, variant, itemReq, unitPrice, promotion);
            orderItemRepository.save(orderItem);

            totalAmount = totalAmount.add(lineAmount);
        }

        // Thêm quà tặng kèm (giá = 0) cho mỗi promotion đã áp dụng trong đơn
        for (Long promoId : appliedPromotionIds) {
            addGiftItemsForPromotion(order, promoId);
        }

        // Lưu tổng COD cho đơn
        order.setCodAmount(totalAmount);
        order.setCreatedAt(LocalDateTime.now());

        // Gán phương thức thanh toán
        order.setPaymentMethod(request.getPaymentMethod());

        // Xóa các item đã mua khỏi giỏ hàng
        removeItemsFromCart(customerId, items);

        orderGHNRepository.save(order);

        // Nhánh theo phương thức thanh toán
        if (request.getPaymentMethod() == EPaymentMethod.COD) {
            // COD: coi như đơn đã xác nhận, chuẩn bị hàng
            order.setStatus(EOrderStatus.CONFIRMED);
            order.setUpdatedAt(LocalDateTime.now());
            orderGHNRepository.save(order);

            return new CheckoutResponse(
                    order.getOrderGhnId(),
                    null,
                    null);
        }

        // VNPAY (BANK_TRANSFER): tạo giao dịch chờ thanh toán
        String bankTxnId = vnPayService.generateBankTxnId();
        PaymentTransaction payment = new PaymentTransaction();
        payment.setOrderGhn(order);
        payment.setBankTxnId(bankTxnId); 
        payment.setAmount(totalAmount);
        payment.setContent("Thanh toan don hang " + order.getOrderGhnId());
        payment.setStatus(EPaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());
        
        payment = paymentTransactionRepository.save(payment);
        // tạo URL
        String paymentUrl = vnPayService.createPaymentUrl(payment);
        payment.setQrCodeUrl(paymentUrl);
        paymentTransactionRepository.save(payment);

        return new CheckoutResponse(
                order.getOrderGhnId(),
                payment.getPaymentTransactionId(),
                paymentUrl);
    }

    /**
     * Xử lý return URL từ VNPAY.
     */
    public void handleVnPayReturn(String bankTxnId, String responseCode) {

        PaymentTransaction payment = paymentTransactionRepository
                .findByBankTxnId(bankTxnId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy giao dịch"));

        OrderGHN order = payment.getOrderGhn();

        if ("00".equals(responseCode)) {

            payment.setStatus(EPaymentStatus.SUCCESS);
            payment.setPaidAt(LocalDateTime.now());

            order.setStatus(EOrderStatus.CONFIRMED);
            order.setUpdatedAt(LocalDateTime.now());

        } else {

            payment.setStatus(EPaymentStatus.FAIL);
            order.setStatus(EOrderStatus.CANCELLED);
            order.setUpdatedAt(LocalDateTime.now());
        }

        payment.setUpdatedAt(LocalDateTime.now());
        paymentTransactionRepository.save(payment);
        orderGHNRepository.save(order);
    }

    // ================= PRIVATE METHODS =================

    private void validateCheckoutRequest(CheckoutRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BusinessException("Không có sản phẩm để thanh toán");
        }
    }

    private OrderGHN buildOrder(CheckoutRequest request) {
        OrderGHN order = new OrderGHN();
        order.setToName(request.getToName());
        order.setToPhone(request.getToPhone());
        order.setToAddress(request.getToAddress());
        order.setToWardCode(request.getToWardCode());
        order.setToProvinceCode(request.getToProvinceCode());
        order.setStatus(EOrderStatus.PENDDING);
        order.setCreatedAt(LocalDateTime.now());
        return order;
    }

    private void validateStock(ProductVariant variant, Integer quantity) {
        if (variant.getStockQuantity() != null &&
                quantity > variant.getStockQuantity()) {
            throw new BusinessException(
                    "Số lượng vượt tồn kho cho sản phẩm " + variant.getSku());
        }
    }

    private BigDecimal applyDiscount(BigDecimal originalPrice, Promotion promotion) {
        BigDecimal price = originalPrice;

        if (promotion.getDiscountPercent() != null && promotion.getDiscountPercent() > 0) {
            BigDecimal percent = BigDecimal.valueOf(promotion.getDiscountPercent())
                    .divide(BigDecimal.valueOf(100));
            price = price.subtract(price.multiply(percent));
        }

        if (promotion.getDiscountAmount() != null && promotion.getDiscountAmount() > 0) {
            price = price.subtract(BigDecimal.valueOf(promotion.getDiscountAmount()));
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            price = BigDecimal.ZERO;
        }

        return price;
    }

    private OrderItem buildOrderItem(OrderGHN order,
                                     ProductVariant variant,
                                     CheckoutItemRequest itemReq,
                                     BigDecimal unitPrice,
                                     Promotion promotion) {

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProductVariantId(variant.getProductVariantId());
        orderItem.setProductName(variant.getProduct().getProductName());
        orderItem.setQuantity(itemReq.getQuantity());
        orderItem.setPrice(unitPrice);
        if (promotion != null) {
            orderItem.setPromotionId(promotion.getPromotionId());
        }

        return orderItem;
    }

    /**
     * Thêm các OrderItem quà tặng (giá = 0) cho promotion.
     */
    private void addGiftItemsForPromotion(OrderGHN order, Long promotionId) {
        List<PromotionItem> promoItems = promotionItemRepository
                .findByPromotion_PromotionId(promotionId)
                .stream()
                .filter(pi -> Boolean.TRUE.equals(pi.getIsGift()))
                .toList();

        for (PromotionItem pi : promoItems) {
            ProductVariant giftVariant = pi.getProductVariant();

            OrderItem giftItem = new OrderItem();
            giftItem.setOrder(order);
            giftItem.setProductVariantId(giftVariant.getProductVariantId());
            giftItem.setProductName(giftVariant.getProduct().getProductName());
            giftItem.setQuantity(pi.getQuanlity());
            giftItem.setPrice(BigDecimal.ZERO);
            giftItem.setPromotionId(promotionId);

            orderItemRepository.save(giftItem);
        }
    }

    /**
     * Xóa các sản phẩm đã checkout khỏi giỏ hàng của customer.
     */
    private void removeItemsFromCart(Long customerId, List<CheckoutItemRequest> items) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElse(null);
        if (cart == null) {
            return;
        }

        for (CheckoutItemRequest itemReq : items) {
            cartItemRepository.findByCartIdAndProductId(cart.getId(), itemReq.getProductVariantId())
                    .ifPresent(cartItemRepository::delete);
        }
    }
}