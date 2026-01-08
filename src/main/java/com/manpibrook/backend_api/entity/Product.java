package com.manpibrook.backend_api.entity;

import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "brand_id")
	private int brandId;
	
	@Column(name="category_id")
	private int categoryId;
	
	private String name;
	private String slug;
	
	@Column(columnDefinition = "longtext")
	private String dipcription;
	
	@Column(columnDefinition = "json")
	private Map<String, String> specifications;
	
	@Column(name ="average_rating")
	private int averageRating;
	
	@Column(name = "is_new")
	private boolean isNew;
	
	@Column(name = "is_best_saler")
	private boolean isBestSaler;
}
