package com.manpibrook.backend_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manpibrook.backend_api.dto.response.CategoryResponse;
import com.manpibrook.backend_api.entity.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{
	// create a pruduct need check category is exsist
	Optional<Category> findByCategoryId(Long id);
	// get all category order by name for category controller
	List<Category> findAllByOrderByName();
	// Delete category by id
	void deleteByCategoryId(Long id);
	
	boolean existsByCategoryId(Long id);
	
}
