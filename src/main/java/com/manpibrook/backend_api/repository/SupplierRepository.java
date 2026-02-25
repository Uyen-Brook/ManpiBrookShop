package com.manpibrook.backend_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manpibrook.backend_api.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
	Optional<Supplier> findBySupplierId(Long long1);

	boolean existsByTaxCode(String taxCode);

	boolean existsBycode(String code);
}
