package com.manpibrook.backend_api.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manpibrook.backend_api.dto.RegionProjection;
import com.manpibrook.backend_api.entity.AdministrativeRegion;
import com.manpibrook.backend_api.repository.common.AdministrativeRegionRepository;

@Service
public class AdministrativeRegionService {
	@Autowired
	private AdministrativeRegionRepository administrativeRepo;
	// lấy tất cả vùng kinh tế theo tên sắp xếp tăng dần
	public List<RegionProjection> getAllRegion(){
		return administrativeRepo.findAllByOrderByNameAsc();
	}
}
