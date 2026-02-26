package com.manpibrook.backend_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manpibrook.backend_api.entity.OrderGHN;

@Repository
public interface OrderGHNRepository extends JpaRepository<OrderGHN, Long> {
}
