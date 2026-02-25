package com.manpibrook.backend_api.service;

import java.util.List;
import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manpibrook.backend_api.dto.request.admin.BrandRequest;
import com.manpibrook.backend_api.dto.response.BrandResponse;
import com.manpibrook.backend_api.entity.Brand;
import com.manpibrook.backend_api.repository.BrandRepository;
import com.manpibrook.backend_api.repository.ProductRepository;

@Service
public class BrandService {
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private ProductRepository productRepositorys;

	public List<BrandResponse> getAllBrand() {
		List<Brand> brands = brandRepository.findAllByOrderByName();
		return brands.stream().map(c -> new BrandResponse(
				c.getBrandId(),
				c.getName(), 
				c.getUrl()))
				.toList();
	}

	public BrandResponse createBrand(BrandRequest request) {
		Brand brand = new Brand();
		brand.setName(request.getName());
		brand.setUrl(request.getUrl());
		brandRepository.save(brand);

		return null;
	}

	public BrandResponse updateBrand(Integer id, BrandRequest request) {
		Brand brand = brandRepository.findByBrandId(id)
				.orElseThrow(() -> new RuntimeException("Brand not found"));

		brand.setName(request.getName());
		brand.setUrl(request.getUrl());

		Brand updatedBrand = brandRepository.save(brand);
		return mapToResponse(updatedBrand);
	}

	// 5. PATCH - Cập nhật một phần (ví dụ: chỉ cập nhật tên hoặc chỉ cập nhật URL)
	public BrandResponse patchBrand(Integer id, BrandRequest request) {
		Brand brand = brandRepository.findByBrandId(id)
				.orElseThrow(() -> new RuntimeException("Brand not found"));

		if (request.getName() != null) {
			brand.setName(request.getName());
		}
		if (request.getUrl() != null) {
			brand.setUrl(request.getUrl());
		}

		Brand patchedBrand = brandRepository.save(brand);
		return mapToResponse(patchedBrand);
	}

	// 6. DELETE - Xóa thương hiệu
	public String deleteBrand(Integer id) {
		if (!brandRepository.existsById(id)) {
			throw new RuntimeException("Brand not found");
		}
		else if (productRepositorys.existsByBrand_BrandId(id)) {
			return "Xóa thất bại vui lòng xóa tất cả sản phẩm trước khi xóa";
		}
		brandRepository.deleteByBrandId(id);
		if(!brandRepository.existsById(id)) {
			return "Xóa brand thành công";
		}
		return "Xóa thất bại";
	}

	private BrandResponse mapToResponse(Brand brand) {
		return new BrandResponse(
				brand.getBrandId(),
				brand.getName(),
				brand.getUrl());
	}
}
