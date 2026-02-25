package com.manpibrook.backend_api.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manpibrook.backend_api.dto.LocationProjection;
import com.manpibrook.backend_api.entity.Province;
@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer>{
	List<LocationProjection> findByAdministrativeUnitIdOrderByNameAsc(Integer administrativeUnitId);
}
