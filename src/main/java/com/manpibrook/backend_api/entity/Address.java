package com.manpibrook.backend_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Address") 
@Getter @Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long adressId;
    
    @Column(name = "account_id")
    private Long accountId;
    
    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "province_id")
    private Integer provinceId;
    
    @Column(name = "district_id")
    private Integer districtId;
    
    @Column(name = "ward_id")
    private Integer wardId;
    
    private String description;
}