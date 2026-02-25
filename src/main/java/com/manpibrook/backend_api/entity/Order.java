package com.manpibrook.backend_api.entity;

import java.math.BigDecimal;

import com.manpibrook.backend_api.entity.enums.EOrderStatus;
import com.manpibrook.backend_api.entity.enums.EPaymentMethod;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "payment_transaction_id")
    private Long paymentTransactionId;
    
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    
    @Column(name = "order_status")
    private String orderStatus;
    
    @Column(name = "shipping_address")
    private String shippingAddress;
    
    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private EPaymentMethod paymentMethod;
    
    @Column(name = "product_name")
    private String productName; 
    
    @Column(name = "shipping_code")
    private String shippingCode;
    
    @Column(name = "shipping_carrier")
    private String shippingCarrier;
    
    @Column(name = "shipping_status")
    @Enumerated(EnumType.STRING)
    private EOrderStatus shippingStatus;
    
    
}
