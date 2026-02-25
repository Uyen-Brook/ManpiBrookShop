package com.manpibrook.backend_api.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "promotion")
@Getter @Setter
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Long promotionId;
    private String name;
    @Column(name = "discount_percent")
    private Double discountPercent;
    @Column(name = "discount_amount")
    private Double discountAmount;
    @Column(name = "gift_description")
    private String giftDescription;
    
    private String giftType;
    private Long giftProductId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
