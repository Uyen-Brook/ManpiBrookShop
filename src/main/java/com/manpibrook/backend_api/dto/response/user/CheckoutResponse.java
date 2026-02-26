package com.manpibrook.backend_api.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CheckoutResponse {

    private Long orderId;
    private Long paymentTransactionId;
    // URL/VNPAY link hiển thị QR code thanh toán
    private String paymentUrl;
}
