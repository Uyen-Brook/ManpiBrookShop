package com.manpibrook.backend_api.entity;
import java.util.List;

import com.manpibrook.backend_api.utils.JsonNodeConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.databind.JsonNode;

@Entity
@Table(name = "product")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name")
    private String productName;
    
    private String slug;
   
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;
    private String thumbnail;
    
    @Convert(converter = JsonNodeConverter.class)
    @Column(name = "specification", columnDefinition = "JSON")
    private JsonNode specification;
    
    @Column(name = "average_rating")
    private Double averageRating;
    
    @Column(name = "base_price")
    private Double basePrice;
    
    @Column(name = "category_id")
    private Long categoryId; 
    
    @Column(name = "brand_id")
    private Long brandId;
    
    @Column(name = "suppler_id")
    private Long supplierId;
    
    @Convert(converter = JsonNodeConverter.class)
    @Column(name = "variant_ids", columnDefinition = "NVARCHAR(MAX)")
    private JsonNode variantIds;
}