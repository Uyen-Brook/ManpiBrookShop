package com.manpibrook.backend_api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.manpibrook.backend_api.entity.enums.EOrderStatus;
import com.manpibrook.backend_api.entity.enums.EPaymentMethod;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_ghn")
@Getter
@Setter
@NoArgsConstructor
public class OrderGHN {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_ghn_id")
    private Long orderGhnId;

    @Column(name = "shipping_order_code")
    private String shippingOrderCode;

    // ================= THÔNG TIN NGƯỜI NHẬN =================

    @Column(name = "to_name", nullable = false)
    private String toName;

    @Column(name = "to_phone", nullable = false)
    private String toPhone;

    @Column(name = "to_address", nullable = false)
    private String toAddress;

    @Column(name = "to_ward_code")
    private String toWardCode;

    @Column(name = "to_province_code")
    private Integer toProvinceCode;

    // ================= THÔNG TIN KIỆN HÀNG =================

    private Integer weight;
    private Integer length;
    private Integer width;
    private Integer height;

    // ================= TÀI CHÍNH =================

    @Column(name = "cod_amount", precision = 15, scale = 2)
    private BigDecimal codAmount;   // đổi từ Double → BigDecimal

    @Column(name = "shipping_fee", precision = 15, scale = 2)
    private BigDecimal shippingFee; // đổi từ Double → BigDecimal

    // ================= TRẠNG THÁI / THANH TOÁN =================

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EOrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private EPaymentMethod paymentMethod;

    // ================= QUAN HỆ =================

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;

    @OneToMany(mappedBy = "orderGhn", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentTransaction> paymentTransactions; // thêm để truy xuất lịch sử

    // ================= KHÁC =================

    @Column(name = "required_note")
    private String requiredNote;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}