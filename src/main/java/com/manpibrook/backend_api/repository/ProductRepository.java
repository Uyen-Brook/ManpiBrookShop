package com.manpibrook.backend_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.manpibrook.backend_api.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Page<Product> findByProductNameContainingIgnoreCase(String name, Pageable pageable);
	
	boolean existsByCategory_CategoryId(Integer id);

	boolean existsByBrand_BrandId(Integer id);

	

}
