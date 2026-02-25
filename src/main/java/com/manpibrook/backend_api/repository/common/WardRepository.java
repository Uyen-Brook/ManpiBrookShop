package com.manpibrook.backend_api.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manpibrook.backend_api.dto.LocationProjection;
import com.manpibrook.backend_api.entity.Ward;
@Repository
public interface WardRepository extends JpaRepository<Ward, Integer>{
//	List<Ward> findByProvinceCode(String proviceCode);
	List<LocationProjection> findByProvince_CodeOrderByNameAsc(String proviceCode);
}
