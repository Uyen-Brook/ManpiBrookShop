package com.manpibrook.backend_api.dto.request.user;

import com.manpibrook.backend_api.entity.enums.OrderIssueType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderIssueRequest {

    @NotNull
    private Long orderId;

    @NotNull
    private OrderIssueType type;

    @NotBlank
    private String reason;
}