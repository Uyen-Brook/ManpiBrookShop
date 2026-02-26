package com.manpibrook.backend_api.dto.request.admin;

import java.time.LocalDate;
import java.util.Date;
import com.manpibrook.backend_api.entity.enums.ERole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest {
    
    private String fullName;
    private String phone;
    private String position;
    private String department;
    private Date dob;
    private String identityNumber;
    private Double salary;
    private LocalDate joinDate;
    
    private String email;
    private String userName;
    private String password;
    private ERole role = ERole.STAFF;
    private Boolean active;
}
