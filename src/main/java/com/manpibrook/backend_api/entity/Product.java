package com.manpibrook.backend_api.entity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.manpibrook.backend_api.utils.JsonMapConverter;

@Entity
@Table(name = "products") // Nên dùng số nhiều cho tên bảng
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Tên gọn là id, mapping với product_id

    @Column(name = "product_name", nullable = false)
    private String productName;
    
    @Column(unique = true, nullable = false)
    private String slug;
   
    @Lob // Tốt hơn columnDefinition nếu muốn database tự chọn kiểu Text lớn nhất
    private String description;
    
    private String thumbnail;
    
    @Convert(converter = JsonMapConverter.class)
    @Column(columnDefinition = "LONGTEXT")
    private Map<String, Object> specification;      

    private Double averageRating; // Để mặc định là 0.0
    
    private BigDecimal basePrice; // Đổi sang BigDecimal
    
    // Mapping quan hệ thay vì dùng ID thuần
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    // Một sản phẩm có nhiều biến thể
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants;

    
    public void addVariant(List<ProductVariant> variants2) {
    	this.variants =variants2; // Rất quan trọng: Gán SP cha cho biến thể
    }
}
