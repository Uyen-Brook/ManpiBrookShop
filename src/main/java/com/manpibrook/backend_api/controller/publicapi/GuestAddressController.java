package com.manpibrook.backend_api.controller.publicapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manpibrook.backend_api.dto.LocationProjection;
import com.manpibrook.backend_api.dto.RegionProjection;
import com.manpibrook.backend_api.service.common.AdministrativeRegionService;
import com.manpibrook.backend_api.service.common.ProvinceService;
import com.manpibrook.backend_api.service.common.WardService;
											//DONE
@RestController
@RequestMapping("api/address")
public class GuestAddressController {
	@Autowired
	private ProvinceService provineService;
	@Autowired
	private AdministrativeRegionService adminisService;
	@Autowired
	private WardService wardService;
	//lấy danh sách vùng
	@GetMapping("/regions")
	public List<RegionProjection> getAllAdministrativeRegion() {
		return adminisService.getAllRegion();
	}
	// lấy danh sách tỉnh theo id vùng
	@GetMapping("/provinces")
	public List<LocationProjection> getProviceByRegionId(@RequestParam("id")Integer regionId) {
		return provineService.getProvincesByRegionsId(regionId);
	}
	// lấy danh sách xã theo id tỉnh
	@GetMapping("/wards")
	public List<LocationProjection> getWardsByProviceCode(@RequestParam("code") String proviceCode) {
		return wardService.getWardsByProvinceCode(proviceCode);
	}
	// => phục vụ chọn để lưu, thêm địa chỉ, đặt đơn hàng
}
