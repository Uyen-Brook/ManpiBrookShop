package com.manpibrook.backend_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.manpibrook.backend_api.entity.ShopInfor;

@Repository
public interface ShopInforRepository extends JpaRepository<ShopInfor, Long> {
}