package com.manpibrook.backend_api.service;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manpibrook.backend_api.dto.request.admin.ProductRequest;
import com.manpibrook.backend_api.dto.request.admin.VariantRequest;
import com.manpibrook.backend_api.dto.response.admin.ProductResponse;
import com.manpibrook.backend_api.entity.Brand;
import com.manpibrook.backend_api.entity.Category;
import com.manpibrook.backend_api.entity.Product;
import com.manpibrook.backend_api.entity.ProductVariant;
import com.manpibrook.backend_api.entity.Supplier;
import com.manpibrook.backend_api.repository.BrandRepository;
import com.manpibrook.backend_api.repository.CategoryRepository;
import com.manpibrook.backend_api.repository.ProductRepository;
import com.manpibrook.backend_api.repository.SupplierRepository;
import com.manpibrook.backend_api.utils.BusinessException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
//Thư viện quan trọng nhất cho phân trang
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;    
    private final ObjectMapper objectMapper;
    private final SupplierRepository supplierRepository;
    
  
    // taoj mới product
    public ProductResponse createProduct(ProductRequest request) {

        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setSlug(request.getSlug());
        product.setDescription(request.getDescription());
        product.setThumbnail(request.getThumbnail());
        product.setSpecification(request.getSpecification());
        product.setBasePrice(request.getBasePrice());
        Category category = categoryRepository.findByCategoryId(request.getCategoryId())
                .orElseThrow(() -> new BusinessException("Category not found"));

        product.setCategory(category);
        Brand brand = brandRepository.findByBrandId(request.getBrandId())
        		.orElseThrow(() -> new BusinessException("brand not found"));
        product.setBrand(brand);
        
        Supplier supplier = supplierRepository.findBySupplierId(request.getSupplierId())
        		.orElseThrow(() -> new BusinessException("Suplier not found"));
        product.setSupplier(supplier);

        List<ProductVariant> variants = request.getVariants().stream()
            .map(v -> {
                ProductVariant variant = new ProductVariant();
                variant.setPrice(v.getPrice());
                variant.setSku(v.getSku());
                variant.setStockQuantity(v.getStockQuantity());
                variant.setAttribute(v.getAttribute());
                variant.setImageList(v.getImageList());
                variant.setProduct(product); // BẮT BUỘC
                return variant;
            })
            .toList();

        product.addVariant(variants);

        Product saved = productRepository.save(product);

        return mapToResponse(saved);
    }

   
    
    public Page<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDir) {
        // Thiết lập sắp xếp (tăng dần hoặc giảm dần)
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) 
                    ? Sort.by(sortBy).ascending() 
                    : Sort.by(sortBy).descending();
        
        // Tạo đối tượng phân trang (page trong Spring Data bắt đầu từ 0)
        Pageable pageable = PageRequest.of(page, size, sort);
        
        // Lấy dữ liệu từ DB
        Page<Product> productPage = productRepository.findAll(pageable);
        
        // Chuyển đổi Page<Product> sang Page<ProductResponse>
        return productPage.map(this::mapToResponse);
    }
    
  
   
    // convert product sang productresponse
    private ProductResponse mapToResponse(Product product) {
        ProductResponse res = new ProductResponse();
        BeanUtils.copyProperties(product, res);
        if (product.getCategory() != null) res.setCategoryName(product.getCategory().getName());
        if (product.getBrand() != null) res.setBrandName(product.getBrand().getName());
        return res;
    }





	
}