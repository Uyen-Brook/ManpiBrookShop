package com.manpibrook.backend_api.entity;

import java.awt.Window.Type;
import java.time.LocalDateTime;
import java.util.List;

import com.manpibrook.backend_api.entity.enums.EOrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_ghn")
@Getter @Setter
@NoArgsConstructor
public class OrderGHN {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_ghn_id")
    private Long orderGhnId;

//    @Column(name = "order_code", unique = true) // Mã đơn hàng nội bộ hoặc mã GHN trả về
//    private String orderCode;
     
    @Column(name = "shipping_order_code") // Mã vận đơn của GHN (VD: ED12345678)
    private String shippingOrderCode;

    // --- Thông tin người nhận ---
    @Column(name = "to_name", nullable = false)
    private String toName;

    @Column(name = "to_phone", nullable = false)
    private String toPhone;

    @Column(name = "to_address", nullable = false)
    private String toAddress;

    @Column(name = "to_ward_code") // Mã xã/phường của GHN
    private String toWardCode;

    @Column(name = "to_province_code") // Mã quận/huyện của GHN
    private Integer toProvinceCode;

    // --- Thông tin kiện hàng ---
    private Integer weight; // Khối lượng (gram)
    private Integer length; // Chiều dài (cm)
    private Integer width;  // Chiều rộng (cm)
    private Integer height; // Chiều cao (cm)

    // --- Tài chính & Trạng thái ---
    @Column(name = "cod_amount")
    private Double codAmount; // Tiền thu hộ

    @Column(name = "shipping_fee")
    private Double shippingFee; // Phí ship trả cho GHN

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EOrderStatus status; // Trạng thái GHN (ready_to_pick, delivering, delivered,...)
   
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;

    @Column(name = "required_note")
    private String requiredNote; // Ghi chú xem hàng (CHOTXEMHANG, CHOXEMHANGKHONGTHU, KHONGCHOXEMHANG)
    
    @Column(name = "create_at")
    private LocalDateTime creatAt;
    
    @Column(name = "update_at")
    private LocalDateTime updateAt;
}