package com.manpibrook.backend_api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.manpibrook.backend_api.entity.enums.EPaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "payment_transactions")
@Getter
@Setter
@NoArgsConstructor
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_transaction_id")
    private Long paymentTransactionId;

    // ================= LIÊN KẾT ĐƠN HÀNG =================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_ghn_id", nullable = false)
    private OrderGHN orderGhn;

    // ================= GIAO DỊCH =================

    @Column(name = "txn_ref", nullable = false, unique = true, length = 50)
    private String txnRef;   // mã giao dịch hệ thống (gửi sang VNPAY)

    @Column(name = "bank_txn_id", unique = true, length = 50)
    private String bankTxnId; // vnp_TransactionNo do VNPAY trả về

    @Column(name = "response_code", length = 10)
    private String responseCode; // vnp_ResponseCode

    @Column(name = "amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EPaymentStatus status;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    // ================= QR CODE =================

    @Column(name = "qr_code_url", length = 500)
    private String qrCodeUrl;

    // ================= AUDIT =================

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}