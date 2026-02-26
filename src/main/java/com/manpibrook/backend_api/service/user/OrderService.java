package com.manpibrook.backend_api.service.user;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.manpibrook.backend_api.dto.request.user.CheckoutItemRequest;
import com.manpibrook.backend_api.dto.request.user.CheckoutRequest;
import com.manpibrook.backend_api.dto.response.user.CheckoutResponse;
import com.manpibrook.backend_api.entity.OrderGHN;
import com.manpibrook.backend_api.entity.OrderItem;
import com.manpibrook.backend_api.entity.PaymentTransaction;
import com.manpibrook.backend_api.entity.ProductVariant;
import com.manpibrook.backend_api.entity.enums.EOrderStatus;
import com.manpibrook.backend_api.entity.enums.EPaymentStatus;
import com.manpibrook.backend_api.repository.OrderGHNRepository;
import com.manpibrook.backend_api.repository.OrderItemRepository;
import com.manpibrook.backend_api.repository.PaymentTransactionRepository;
import com.manpibrook.backend_api.repository.ProductVariantRepository;
import com.manpibrook.backend_api.utils.BusinessException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderGHNRepository orderGHNRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final ProductVariantRepository productVariantRepository;
    private final VnPayService vnPayService;

    /**
     * Checkout và tạo URL thanh toán VNPAY
     */
    public CheckoutResponse checkoutWithVnPay(Long customerId, CheckoutRequest request) {

        validateCheckoutRequest(request);

        // 1️⃣ Tạo Order
        OrderGHN order = buildOrder(request);
        order = orderGHNRepository.save(order);

        // 2️⃣ Tạo OrderItem + tính tổng tiền
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CheckoutItemRequest itemReq : request.getItems()) {

            ProductVariant variant = productVariantRepository.findById(itemReq.getProductVariantId())
                    .orElseThrow(() -> new BusinessException("Không tìm thấy sản phẩm"));

            validateStock(variant, itemReq.getQuantity());

            BigDecimal lineAmount = variant.getPrice()
                    .multiply(BigDecimal.valueOf(itemReq.getQuantity()));

            OrderItem orderItem = buildOrderItem(order, variant, itemReq);
            orderItemRepository.save(orderItem);

            totalAmount = totalAmount.add(lineAmount);
        }

        // 3️⃣ Tạo PaymentTransaction
        PaymentTransaction payment = new PaymentTransaction();
        payment.setOrderGhn(order);
        payment.setAmount(totalAmount.doubleValue());
        payment.setContent("Thanh toán đơn hàng #" + order.getOrderGhnId());
        payment.setStatus(EPaymentStatus.PENDING);
        payment.setBankTxnId(vnPayService.generateBankTxnId());

        payment = paymentTransactionRepository.save(payment);

        // 4️⃣ Tạo URL VNPAY
        String paymentUrl = vnPayService.createPaymentUrl(payment);
        payment.setQrCodeUrl(paymentUrl);
        paymentTransactionRepository.save(payment);

        return new CheckoutResponse(
                order.getOrderGhnId(),
                payment.getPaymentTransactionId(),
                paymentUrl
        );
    }

    /**
     * Xử lý return URL từ VNPAY
     */
    public void handleVnPayReturn(String bankTxnId, String responseCode) {

        PaymentTransaction payment = paymentTransactionRepository
                .findByBankTxnId(bankTxnId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy giao dịch"));

        OrderGHN order = payment.getOrderGhn();

        if ("00".equals(responseCode)) { // Thành công

            payment.setStatus(EPaymentStatus.SUCCESS);

            order.setStatus(EOrderStatus.CONFIRMED);
            order.setUpdateAt(LocalDateTime.now());

        } else {

            payment.setStatus(EPaymentStatus.FAILED);

            order.setStatus(EOrderStatus.CANCELLED);
            order.setUpdateAt(LocalDateTime.now());
        }

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
        order.setStatus(EOrderStatus.PENDING);
        order.setCreatAt(LocalDateTime.now());
        return order;
    }

    private void validateStock(ProductVariant variant, Integer quantity) {
        if (variant.getStockQuantity() != null &&
                quantity > variant.getStockQuantity()) {
            throw new BusinessException(
                    "Số lượng vượt tồn kho cho sản phẩm " + variant.getSku()
            );
        }
    }

    private OrderItem buildOrderItem(OrderGHN order,
                                     ProductVariant variant,
                                     CheckoutItemRequest itemReq) {

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProductVariantId(variant.getProductVariantId());
        orderItem.setProductName(variant.getProduct().getProductName());
        orderItem.setQuantity(itemReq.getQuantity());
        orderItem.setPrice(variant.getPrice());
        orderItem.setPromotionId(variant.getPromotionId());

        return orderItem;
    }
}