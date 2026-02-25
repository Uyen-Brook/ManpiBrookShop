package com.manpibrook.backend_api.entity;

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
import lombok.Setter;

@Entity
@Table(name = "payment_transactions")
@Getter @Setter
public class PaymentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_transaction_id")
    private Long paymentTransactionId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_ghn_id", nullable = false) 
    private OrderGHN orderGhn;
    
    private Double amount;
    private String content;
    
 // Link ảnh QR động đã tạo từ VietQR API
    @Column(name = "qr_code_url", length = 500)
    private String qrCodeUrl;
    
 // Trạng thái: PENDING, SUCCESS, FAILED, EXPIRED
    @Enumerated(EnumType.STRING)
    private EPaymentStatus status;
    
    @Column(name = "bank_txn_id", unique = true)
    private String bankTxnId;
    
    @Column(name = "transfer_time")
    private LocalDateTime transferTime;
}