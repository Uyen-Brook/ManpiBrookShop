package com.manpibrook.backend_api.service.common;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manpibrook.backend_api.dto.LocationProjection;
import com.manpibrook.backend_api.entity.Province;
import com.manpibrook.backend_api.repository.common.ProvinceRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProvinceService{
	@Autowired
	private ProvinceRepository provinceRepository;
	// dánh sách tỉnh theo vùng miền
	public List<LocationProjection> getProvincesByRegionsId(Integer id) {
		// TODO Auto-generated method stub
		List<LocationProjection> provinces = provinceRepository.findByAdministrativeUnitIdOrderByNameAsc(id);
		if(provinces == null) {
			System.err.println("không tìm thấy dữ liệu provice");
			throw new EntityNotFoundException();
		}
		return provinces;
	}

}
