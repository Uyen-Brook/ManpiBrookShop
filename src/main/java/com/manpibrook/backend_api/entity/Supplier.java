package com.manpibrook.backend_api.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "supplier")
public class Supplier{
    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "contact_fullname")
    private String contactFullname;

    @Column(name = "contact_email")
    private String contactEmail;
    @Column(name = "contact_phone")
    private String contactPhone;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "tax_code")
    private String taxCode;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "fax")
    private String fax;
    @Column(name = "website")
    private String website;
    private String adressUrl;
    private String address;
    @Column(name = "description")
    private String description;
    @Column(name = "note")
    private String note;
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    private Integer status;
   
}
