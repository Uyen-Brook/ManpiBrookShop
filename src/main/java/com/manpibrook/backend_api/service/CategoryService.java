package com.manpibrook.backend_api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manpibrook.backend_api.dto.request.admin.CategoryRequest;
import com.manpibrook.backend_api.dto.response.CategoryResponse;
import com.manpibrook.backend_api.entity.Category;

import com.manpibrook.backend_api.repository.CategoryRepository;
import com.manpibrook.backend_api.repository.ProductRepository;
import com.manpibrook.backend_api.utils.BusinessException;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	// tìm tắt cả category
	public List<CategoryResponse> findAllByOrderByName() {
		// TODO Auto-generated method stub
		List<Category> categories = categoryRepository.findAllByOrderByName();

		return categories.stream()
		        .map(c -> new CategoryResponse(
		                c.getCategoryId(),
		                c.getName(),
		                c.getSlug(),
		                c.getIcon()
		        ))
		        .toList();

	}
	// lưu 1 danh mục.

	public CategoryResponse createCategory(CategoryRequest request) {
		Category category = new Category();
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setIcon(request.getIcon());
        Category savedCategory = categoryRepository.save(category);
		return maptoRespone(savedCategory);
	}
	// cap nhat 1 danh muc
	public CategoryResponse updateCategoty(Long id, CategoryRequest request) {
		 Category category = categoryRepository.findByCategoryId(id)
	                .orElseThrow(() -> new RuntimeException("Category not found"));
		 if (request.getName() != null) {
			    category.setName(request.getName());
			}
			if (request.getSlug() != null) {
			    category.setSlug(request.getSlug());
			}

			if (request.getIcon() != null) {
			    category.setIcon(request.getIcon());
			}
			return maptoRespone(categoryRepository.save(category));
		 }
	
	// xóa theo id
	
	@Transactional
	public String deleteCategory(Long id) {
		if (!categoryRepository.existsByCategoryId(id)) {
	        return ("Không tìm thấy Category với ID: " + id);
	    }
		if (productRepository.existsByCategory_CategoryId(id)) {
	        return "Lỗi: Không thể xóa danh mục này vì vẫn còn sản phẩm thuộc danh mục. Hãy xóa sản phẩm trước!";
	    }
	     categoryRepository.deleteByCategoryId(id);
	     return "Đã xóa thành công";
	}


	 // map tu entity sang response
	 private CategoryResponse maptoRespone(Category category) {
	        CategoryResponse res = new CategoryResponse();
	        BeanUtils.copyProperties(category, res);
	        return res;
	    }
	
	

}
