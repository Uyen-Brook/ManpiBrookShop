package com.manpibrook.backend_api.dto.request.admin;

//ShopInforRequest.java
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ShopInforRequest {
 private String fromName;
 private String toPhone;
 private String toAddress;
 private String toWardCode;
 private Integer toProvinceCode;
}