package com.manpibrook.backend_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.manpibrook.backend_api.entity.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByCategoryId(Integer id);

    List<Category> findAllByOrderByName();

    void deleteByCategoryId(Integer id);

    boolean existsByCategoryId(Integer id);
}
