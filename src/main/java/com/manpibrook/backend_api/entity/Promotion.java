package com.manpibrook.backend_api.entity;

import java.time.LocalDateTime;

import com.manpibrook.backend_api.entity.enums.EGiftType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    
    @Enumerated(EnumType.STRING)
    private EGiftType giftType;
    private Long giftProductId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
