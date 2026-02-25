package com.manpibrook.backend_api.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manpibrook.backend_api.dto.request.admin.BrandRequest;
import com.manpibrook.backend_api.dto.response.BrandResponse;
import com.manpibrook.backend_api.service.BrandService;

@RestController
@RequestMapping("/api/admin/brand")
public class BrandController {
	@Autowired
	private BrandService brandSerVice;
	
	@GetMapping
	public ResponseEntity<List<BrandResponse>> getAllBrand(){
		return ResponseEntity.ok(brandSerVice.getAllBrand());
	}
	@PostMapping("/create")
	public ResponseEntity<BrandResponse> createBrand(@RequestBody BrandRequest request){
		return ResponseEntity.ok(brandSerVice.createBrand(request));
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<BrandResponse> updateBrand(@PathVariable Integer id,@RequestBody BrandRequest request){
		return ResponseEntity.ok(brandSerVice.updateBrand(id, request));
	}
	@PatchMapping("/update/{id}")
	public ResponseEntity<BrandResponse> update(@PathVariable Integer id,@RequestBody BrandRequest request){
		return ResponseEntity.ok(brandSerVice.patchBrand(id, request));
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteBrand(@PathVariable Integer id){
		return ResponseEntity.ok(brandSerVice.deleteBrand(id));
	}
}
