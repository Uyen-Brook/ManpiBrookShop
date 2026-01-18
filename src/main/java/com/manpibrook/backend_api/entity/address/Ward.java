package com.manpibrook.backend_api.entity.address;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Ward", schema = "dbo")
@Getter @Setter
public class Ward {
    @Id
    private String code;
    private String name;
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "full_name_en")
    private String fullNameEn;
    @Column(name = "code_name")
    private String codeName;
    @Column(name = "province_code")
    private String provinceCode;
    
}