package com.manpibrook.backend_api.entity;

import java.time.LocalDateTime;

import com.manpibrook.backend_api.entity.enums.OrderIssueType;

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
@Table(name = "order_issues")
@Getter
@Setter
public class OrderIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_issue_id")
    private Long id;

    @Column(name = "order_ghn_id", nullable = false)
    private Long orderGhnId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private OrderIssueType type; // CANCEL / RETURN

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "handled", nullable = false)
    private boolean handled = false;
}

