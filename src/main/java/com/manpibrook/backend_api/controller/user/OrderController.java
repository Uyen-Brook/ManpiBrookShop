package com.manpibrook.backend_api.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manpibrook.backend_api.dto.request.user.CheckoutRequest;
import com.manpibrook.backend_api.dto.request.user.OrderIssueRequest;
import com.manpibrook.backend_api.dto.response.user.CheckoutResponse;
import com.manpibrook.backend_api.service.user.OrderIssueService;
import com.manpibrook.backend_api.service.user.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderIssueService orderIssueService;

    /**
     * Người dùng chọn 1 hoặc nhiều item từ giỏ hàng, nhập thông tin nhận hàng
     * và chọn phương thức thanh toán (COD hoặc VNPAY).
     * Tạm thời truyền customerId trên path, sau này có JWT thì lấy từ token.
     */
    @PostMapping("/checkout/{customerId}")
    public ResponseEntity<CheckoutResponse> checkout(
            @PathVariable Long customerId,
            @Valid @RequestBody CheckoutRequest request) {

        CheckoutResponse response = orderService.checkoutWithVnPay(customerId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * URL mà VNPAY redirect về sau khi thanh toán (cấu hình trong vnpay.return-url).
     * VNPAY sẽ gửi lại mã giao dịch (vnp_TxnRef) và mã phản hồi (vnp_ResponseCode).
     */
    @GetMapping("/vnpay-return")
    public ResponseEntity<?> handleVnPayReturn(
            @RequestParam("vnp_TxnRef") String bankTxnId,
            @RequestParam("vnp_ResponseCode") String responseCode) {

        orderService.handleVnPayReturn(bankTxnId, responseCode);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Cập nhật trạng thái thanh toán thành công"
        ));
    }

    /**
     * Khách hủy đơn / yêu cầu hoàn hàng.
     * Lưu yêu cầu để nhân viên xử lý.
     */
    @PostMapping("/issue/{customerId}")
    public ResponseEntity<?> createOrderIssue(
            @PathVariable Long customerId,
            @Valid @RequestBody OrderIssueRequest request) {

        orderIssueService.createIssue(customerId, request);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Gửi yêu cầu xử lý đơn hàng thành công"
        ));
    }
}
