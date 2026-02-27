package com.manpibrook.backend_api.dto.request.user;

import java.util.List;

import com.manpibrook.backend_api.entity.enums.EPaymentMethod;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutRequest {

    // Thông tin người nhận / địa chỉ giao
    @NotNull
    private String toName;

    @NotNull
    private String toPhone;

    @NotNull
    private String toAddress;

    private String toWardCode;
    private Integer toProvinceCode;

    // Phương thức thanh toán: COD / BANK_TRANSFER (VNPAY)
    @NotNull
    private EPaymentMethod paymentMethod;

    // Các item được chọn từ giỏ hàng
    @NotEmpty
    private List<CheckoutItemRequest> items;
}


