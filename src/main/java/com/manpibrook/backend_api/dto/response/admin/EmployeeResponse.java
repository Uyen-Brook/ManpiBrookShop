package com.manpibrook.backend_api.dto.response.admin;

import java.time.LocalDate;
import java.util.Date;
import com.manpibrook.backend_api.entity.enums.ERole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeResponse {

    private Long id;
    private String fullName;
    private String phone;
    private String imageUrl;
    private String position;
    private String department;
    private Date dob;
    private String identityNumber;
    private Double salary;
    private LocalDate joinDate;

    // Thông tin tài khoản
    private Long accountId;
    private String email;
    private String userName;
    private ERole role;
    private boolean active;
}
