package com.manpibrook.backend_api.entity.address;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "Province", schema = "dbo")
@Getter @Setter
public class Province {
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
    @Column(name = "administrative_unit_id")
    private Integer administrativeUnitId;
}