package com.manpibrook.backend_api.controller.admin;

import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manpibrook.backend_api.dto.request.admin.CategoryRequest;
import com.manpibrook.backend_api.dto.response.CategoryResponse;
import com.manpibrook.backend_api.service.CategoryService;

@RestController
@RequestMapping("api/admin/category")
public class CategoryController {
	@Autowired
	private CategoryService CategoryService;
	
	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getAllCategory(){ 
		
		return ResponseEntity.ok(CategoryService.findAllByOrderByName());
	}
	
	@PostMapping("/create")
	public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request){
		return ResponseEntity.ok(CategoryService.createCategory(request));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, 
			@RequestBody CategoryRequest request){
		return ResponseEntity.ok(CategoryService.updateCategoty(id, request));
	}
	
	// xoa bang id
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		CategoryService.deleteCategory(id);
		return ResponseEntity.ok(CategoryService.deleteCategory(id));
	}
}
