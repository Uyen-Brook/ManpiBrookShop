package com.manpibrook.backend_api.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.databind.JsonNode;
import com.manpibrook.backend_api.utils.JsonListConverter;
import com.manpibrook.backend_api.utils.JsonMapConverter;


@Entity
@Table(name = "product_variants")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_variant_id")
    private Long productVariantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; 

    private BigDecimal price; 
    
    @Column(unique = true)
    private String sku; 
    
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    
    private Integer weight;

    @Convert(converter = JsonMapConverter.class)
    @Column(columnDefinition = "LONGTEXT")
    private  Map<String, Object> attribute; // Ví dụ: {"color": "red", "size": "XL"}
    
    @Convert(converter = JsonListConverter.class)
    @Column(name = "image_list", columnDefinition = "LONGTEXT")
    private List<String> imageList;

    @Column(name = "promotion_id")
    private Long promotionId; // Nếu có bảng Promotion, nên map thành Entity
}
