package com.manpibrook.backend_api.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manpibrook.backend_api.dto.LocationProjection;
import com.manpibrook.backend_api.entity.Ward;
import com.manpibrook.backend_api.repository.common.WardRepository;
@Service
public class WardService {
	@Autowired
	private WardRepository wardRepository;
	public List<LocationProjection> getWardsByProvinceCode(String proviceCode) {
		
		return wardRepository.findByProvince_CodeOrderByNameAsc(proviceCode);
	}

}
