package com.manpibrook.backend_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "shop_infor")
@Getter @Setter
public class ShopInfor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_infor_id")
    private Long id;

    @Column(name = "from_name", nullable = false)
    private String fromName;

    @Column(name = "to_phone", nullable = false)
    private String toPhone;

    @Column(name = "to_address", nullable = false)
    private String toAddress;

    @Column(name = "to_ward_code")
    private String toWardCode;

    @Column(name = "to_province_code")
    private Integer toProvinceCode;
}
