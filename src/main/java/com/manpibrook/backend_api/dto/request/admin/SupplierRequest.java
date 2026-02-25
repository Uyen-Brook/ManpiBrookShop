package com.manpibrook.backend_api.dto.request.admin;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SupplierRequest {
	private String displayName;
    private String code;
    private String contactFullname;
    private String contactEmail;
    private String contactPhone;
    private String companyName;
    private String taxCode;
    private String email;
    private String phone;
    private String fax;
    private String website;
    private String adressUrl;
    private String address;
    private String description;
    private String note;
    private Integer status;
}
