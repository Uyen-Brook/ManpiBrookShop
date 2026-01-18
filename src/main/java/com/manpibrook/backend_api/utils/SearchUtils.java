package com.manpibrook.backend_api.utils;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class SearchUtils {

    /**
     * Tạo Specification để tìm kiếm LIKE (OR) trên nhiều trường
     * @param search Từ khóa tìm kiếm (ví dụ: "iphone")
     * @param searchFields Danh sách các trường (ví dụ: List.of("productName", "sku"))
     */
    public static <T> Specification<T> parse(String search, List<String> searchFields) {
        return (root, query, cb) -> {
            if (search == null || search.isBlank() || searchFields == null || searchFields.isEmpty()) {
                return cb.conjunction();
            }

            String pattern = "%" + search.trim().toLowerCase() + "%";
            List<Predicate> predicates = new ArrayList<>();

            for (String field : searchFields) {
                // Tạo điều kiện: lower(field) LIKE %keyword%
                predicates.add(cb.like(cb.lower(root.get(field).as(String.class)), pattern));
            }

            // Kết hợp các điều kiện bằng toán tử OR
            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }
}
