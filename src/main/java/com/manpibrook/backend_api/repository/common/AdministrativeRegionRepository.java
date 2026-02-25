package com.manpibrook.backend_api.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manpibrook.backend_api.dto.RegionProjection;
import com.manpibrook.backend_api.entity.AdministrativeRegion;

@Repository
public interface AdministrativeRegionRepository extends JpaRepository<AdministrativeRegion, Integer>{
	List<RegionProjection> findAllByOrderByNameAsc();
}
