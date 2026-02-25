package com.manpibrook.backend_api.dto.response;

//ShopInforResponse.java

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ShopInforResponse {
 private Long id;
 private String fromName;
 private String toPhone;
 private String toAddress;
 private String toWardCode;
 private Integer toProvinceCode;
}
