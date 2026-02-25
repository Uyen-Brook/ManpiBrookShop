package com.manpibrook.backend_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manpibrook.backend_api.entity.ShopInfor;

public interface ShopInforRepository extends JpaRepository<ShopInfor, Long> {
}