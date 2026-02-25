package com.manpibrook.backend_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manpibrook.backend_api.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	boolean existsByPhone(String phone);
}