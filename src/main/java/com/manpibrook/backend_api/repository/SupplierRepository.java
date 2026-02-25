package com.manpibrook.backend_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manpibrook.backend_api.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
	Optional<Supplier> findBySupplierId(Integer id);

    List<Supplier> findAllByOrderByDisplayName();
    boolean existsBySupplierId(Integer id);
    boolean existsByCode(String code);
    
    boolean existsByTaxCode(String taxCode);

    boolean existsByCodeAndSupplierIdNot(String code, Integer supplierId);

    boolean existsByTaxCodeAndSupplierIdNot(String taxCode, Integer supplierId);
}