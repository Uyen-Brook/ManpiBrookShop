package com.manpibrook.backend_api.entity;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Employee extends Person {
    private String position;
    
    private String department;
    
    @Column(name = "date_of_birth")
    private Date dob;
    
    @Column(name = "identity_number",unique = true, length = 20)
    private String identityNumber;

    private Double salary;

    @Column(name = "join_date")
    private LocalDate joinDate;
    // Tính thâm niên (không lưu vào DB)
    @Transient
    public int getSeniorityYears() {
        if (joinDate == null) return 0;
        return LocalDate.now().getYear() - joinDate.getYear();
    }
}