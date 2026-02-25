package com.manpibrook.backend_api.dto.request.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SupplierCreateRequest {

    @NotBlank(message = "Tên hiển thị không được để trống")
    @Size(max = 255, message = "Tên hiển thị tối đa 255 ký tự")
    private String displayName;

    @NotBlank(message = "Mã nhà cung cấp không được để trống")
    @Size(max = 50, message = "Mã tối đa 50 ký tự")
    private String code;

    @Size(max = 255, message = "Tên người liên hệ tối đa 255 ký tự")
    private String contactFullname;

    @Email(message = "Email liên hệ không đúng định dạng")
    @Size(max = 255, message = "Email liên hệ tối đa 255 ký tự")
    private String contactEmail;

    @Pattern(regexp = "^(0|\\+84)[0-9]{9,10}$",
             message = "Số điện thoại liên hệ không đúng định dạng Việt Nam")
    private String contactPhone;

    @Size(max = 255, message = "Tên công ty tối đa 255 ký tự")
    private String companyName;

    @Size(max = 20, message = "Mã số thuế tối đa 20 ký tự")
    private String taxCode;

    @Email(message = "Email không đúng định dạng")
    @Size(max = 255, message = "Email tối đa 255 ký tự")
    private String email;

    @Pattern(regexp = "^(0|\\+84)[0-9]{9,10}$",
             message = "Số điện thoại không đúng định dạng Việt Nam")
    private String phone;

    @Size(max = 50, message = "Fax tối đa 50 ký tự")
    private String fax;

    @Size(max = 255, message = "Website tối đa 255 ký tự")
    private String website;

    @Size(max = 500, message = "Đường dẫn địa chỉ tối đa 500 ký tự")
    private String adressUrl;

    @Size(max = 500, message = "Địa chỉ tối đa 500 ký tự")
    private String address;

    @Size(max = 1000, message = "Mô tả tối đa 1000 ký tự")
    private String description;

    @Size(max = 1000, message = "Ghi chú tối đa 1000 ký tự")
    private String note;

    // 0 = inactive, 1 = active
    private Integer status;
}