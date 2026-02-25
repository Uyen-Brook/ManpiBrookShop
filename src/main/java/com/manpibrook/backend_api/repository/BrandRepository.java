package com.manpibrook.backend_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.manpibrook.backend_api.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
	Optional<Brand> findByBrandId(Integer id);

	List<Brand> findAllByOrderByName();

	void deleteByBrandId(Integer id);

}
