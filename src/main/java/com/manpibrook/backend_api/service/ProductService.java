package com.manpibrook.backend_api.service;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manpibrook.backend_api.dto.request.admin.ProductRequest;
import com.manpibrook.backend_api.dto.request.admin.ProductVariantRequest;
import com.manpibrook.backend_api.dto.response.admin.ProductResponse;
import com.manpibrook.backend_api.dto.response.admin.VariantResponse;
import com.manpibrook.backend_api.entity.Brand;
import com.manpibrook.backend_api.entity.Category;
import com.manpibrook.backend_api.entity.Product;
import com.manpibrook.backend_api.entity.ProductVariant;
import com.manpibrook.backend_api.entity.Supplier;
import com.manpibrook.backend_api.repository.BrandRepository;
import com.manpibrook.backend_api.repository.CategoryRepository;
import com.manpibrook.backend_api.repository.ProductRepository;
import com.manpibrook.backend_api.repository.ProductVariantRepository;
import com.manpibrook.backend_api.repository.SupplierRepository;
import com.manpibrook.backend_api.entity.enums.EUploadType;
import com.manpibrook.backend_api.utils.BusinessException;
import com.manpibrook.backend_api.utils.FileUploadUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.BeanUtils;
//Thư viện quan trọng nhất cho phân trang
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    @Value("${upload.path}")
    private String uploadDir;

    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;    
    private final ObjectMapper objectMapper;
    private final SupplierRepository supplierRepository;
    
  
    // taoj mới product
    public ProductResponse createProduct(
            ProductRequest request,
            MultipartFile thumbnail,
            MultipartFile[] variantImages
    ) {

        // =============================
        // 1️⃣ LOAD RELATIONS
        // =============================

        Category category = categoryRepository.findByCategoryId(request.getCategoryId())
                .orElseThrow(() -> new BusinessException("Category not found"));

        Brand brand = brandRepository.findByBrandId(request.getBrandId())
                .orElseThrow(() -> new BusinessException("Brand not found"));

        Supplier supplier = supplierRepository.findBySupplierId(request.getSupplierId())
                .orElseThrow(() -> new BusinessException("Supplier not found"));

        // =============================
        // 2️⃣ CREATE PRODUCT
        // =============================

        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setSlug(request.getSlug());
        product.setDescription(request.getDescription());
        product.setSpecification(request.getSpecificationAsMap());
        product.setBasePrice(request.getBasePrice());
        product.setAverageRating(0.0);
        product.setCategory(category);
        product.setBrand(brand);
        product.setSupplier(supplier);

        productRepository.save(product); // save để có ID

        // =============================
        // 3️⃣ UPLOAD THUMBNAIL
        // =============================

        if (thumbnail != null && !thumbnail.isEmpty()) {
            try {
                String thumbnailPath = FileUploadUtil.saveFile(
                        uploadDir,
                        EUploadType.PRODUCT,
                        product.getId(),
                        thumbnail
                );
                product.setThumbnail(thumbnailPath);
            } catch (IOException e) {
                throw new BusinessException("Upload thumbnail failed");
            }
        }

        // =============================
        // 4️⃣ CREATE VARIANTS
        // =============================

        if (request.getVariants() != null && !request.getVariants().isEmpty()) {

            for (ProductVariantRequest v : request.getVariants()) {

                ProductVariant variant = new ProductVariant();
                variant.setPrice(v.getPrice());
                variant.setSku(v.getSku());
                variant.setStockQuantity(v.getStockQuantity());
                variant.setAttribute(v.getAttributeAsMap());
                variant.setPromotionId(v.getPromotionId());

                product.addVariant(variant); // chuẩn JPA 2 chiều
            }
        }

        // =============================
        // 5️⃣ UPLOAD VARIANT IMAGES
        // =============================

        if (variantImages != null
                && variantImages.length > 0
                && product.getVariants() != null
                && !product.getVariants().isEmpty()) {

            try {
                List<String> imagePaths = FileUploadUtil.saveMultipleFiles(
                        uploadDir,
                        EUploadType.PRODUCT,
                        product.getId(),
                        variantImages
                );

                // Demo: gán cho variant đầu tiên
                product.getVariants().get(0).setImageList(imagePaths);

            } catch (IOException e) {
                throw new BusinessException("Upload variant images failed");
            }
        }

        productRepository.save(product);

        return mapToResponse(product);
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
        
        // Map variants
        if (product.getVariants() != null) {
            List<VariantResponse> variantResponses = product.getVariants().stream()
                .map(this::mapVariantToResponse)
                .collect(Collectors.toList());
            res.setVariants(variantResponses);
        }
        
        return res;
    }
    
    // Convert variant sang variant response
    private VariantResponse mapVariantToResponse(ProductVariant variant) {
        VariantResponse res = new VariantResponse();
        res.setProductVariantId(variant.getProductVariantId());
        res.setPrice(variant.getPrice());
        res.setSku(variant.getSku());
        res.setStockQuantity(variant.getStockQuantity());
        res.setWeight(variant.getWeight());
        res.setAttribute(variant.getAttribute()); // Map<String, Object> - converter đã parse từ JSON
        res.setImageList(variant.getImageList()); // List<String> - converter đã parse từ JSON
        res.setPromotionId(variant.getPromotionId());
        return res;
    }
    // Cập nhật sản phẩm
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Product not found with id: " + id));
        
        // Cập nhật thông tin sản phẩm
        if (request.getProductName() != null) {
            product.setProductName(request.getProductName());
        }
        if (request.getSlug() != null) {
            product.setSlug(request.getSlug());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
   
        if (request.getSpecification() != null) {
            product.setSpecification(request.getSpecificationAsMap());
        }
        if (request.getBasePrice() != null) {
            product.setBasePrice(request.getBasePrice());
        }
        
        // Cập nhật category nếu có
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findByCategoryId(request.getCategoryId())
                    .orElseThrow(() -> new BusinessException("Category not found"));
            product.setCategory(category);
        }
        
        // Cập nhật brand nếu có
        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findByBrandId(request.getBrandId())
                    .orElseThrow(() -> new BusinessException("Brand not found"));
            product.setBrand(brand);
        }
        
        // Cập nhật supplier nếu có
        if (request.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findBySupplierId(request.getSupplierId())
                    .orElseThrow(() -> new BusinessException("Supplier not found"));
            product.setSupplier(supplier);
        }
        
        // Cập nhật variants nếu có
        if (request.getVariants() != null) {

            for (ProductVariantRequest v : request.getVariants()) {

                ProductVariant variant = new ProductVariant();
                variant.setSku(v.getSku());
                variant.setPrice(v.getPrice());
                variant.setStockQuantity(v.getStockQuantity());
                variant.setAttribute(v.getAttributeAsMap());
                variant.setPromotionId(v.getPromotionId());

                product.addVariant(variant); 
            }
        }
        
        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }
    
    // Xóa sản phẩm (và xóa ảnh khỏi đĩa)
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Product not found with id: " + id));
        // Xóa thumbnail sản phẩm
        FileUploadUtil.deleteOldFile(uploadDir, product.getThumbnail());
        // Xóa ảnh trong variants
        if (product.getVariants() != null) {
            product.getVariants().forEach(v -> {
                if (v.getImageList() != null) {
                    v.getImageList().forEach(imgPath -> FileUploadUtil.deleteOldFile(uploadDir, imgPath));
                }
            });
        }
        productRepository.delete(product);
    }
    
    // Lấy sản phẩm theo ID
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Product not found with id: " + id));
        return mapToResponse(product);
    }
    
    // Tìm kiếm sản phẩm theo tên (có phân trang)
    public Page<ProductResponse> getProductsByName(String name, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) 
                    ? Sort.by(sortBy).ascending() 
                    : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findByProductNameContainingIgnoreCase(name, pageable);
        
        return productPage.map(this::mapToResponse);
    }
    
    // Thêm variant vào sản phẩm đã tồn tại
    public ProductResponse addVariantToProduct(Long productId, ProductVariantRequest variantRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Product not found with id: " + productId));
        
        ProductVariant variant = new ProductVariant();
        variant.setPrice(variantRequest.getPrice());
        variant.setSku(variantRequest.getSku());
        variant.setStockQuantity(variantRequest.getStockQuantity());
        // Parse attribute và imageList từ String (JSON) nếu cần
        variant.setAttribute(variantRequest.getAttributeAsMap());
        variant.setImageList(variantRequest.getImageListAsList());
        variant.setPromotionId(variantRequest.getPromotionId());
        variant.setProduct(product);
        
        productVariantRepository.save(variant);
        
        // Reload product để lấy variants mới
        Product updatedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Product not found"));
        return mapToResponse(updatedProduct);
    }
    
    // Cập nhật variant
    public VariantResponse updateVariant(Long variantId, ProductVariantRequest variantRequest) {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new BusinessException("Variant not found with id: " + variantId));
        
        if (variantRequest.getPrice() != null) {
            variant.setPrice(variantRequest.getPrice());
        }
        if (variantRequest.getSku() != null) {
            variant.setSku(variantRequest.getSku());
        }
        if (variantRequest.getStockQuantity() != null) {
            variant.setStockQuantity(variantRequest.getStockQuantity());
        }
        if (variantRequest.getAttribute() != null) {
            variant.setAttribute(variantRequest.getAttributeAsMap());
        }
        if (variantRequest.getImageList() != null) {
            variant.setImageList(variantRequest.getImageListAsList());
        }
        if (variantRequest.getPromotionId() != null) {
            variant.setPromotionId(variantRequest.getPromotionId());
        }
        
        ProductVariant saved = productVariantRepository.save(variant);
        return mapVariantToResponse(saved);
    }
    
    // Xóa variant
    public void deleteVariant(Long variantId) {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new BusinessException("Variant not found with id: " + variantId));
        productVariantRepository.delete(variant);
    }

    /**
     * Upload ảnh thumbnail sản phẩm vào thư mục theo EUploadType.PRODUCT.
     * Xóa file cũ nếu có, lưu file mới, cập nhật vào DB và trả về đường dẫn.
     * Tương tự AuthService.updateAvatar()
     */
    public String uploadThumbnail(Long productId, MultipartFile file) throws IOException {
        // 1. Kiểm tra sản phẩm tồn tại (nếu productId > 0)
        Product product = null;
        if (productId != null && productId > 0) {
            product = productRepository.findById(productId)
                    .orElseThrow(() -> new BusinessException("Không tìm thấy sản phẩm id: " + productId));
        }

        // 2. Xóa file vật lý cũ trên ổ đĩa (nếu có) để tránh rác server
        if (product != null && product.getThumbnail() != null) {
            FileUploadUtil.deleteOldFile(uploadDir, product.getThumbnail());
        }

        // 3. Lưu file mới với tên quy củ: PRD_ID_TIMESTAMP.png
        // Sử dụng EUploadType.PRODUCT để vào đúng thư mục 'products'
        long targetId = productId != null && productId > 0 ? productId : 0L;
        String fileName = FileUploadUtil.saveFile(uploadDir, EUploadType.PRODUCT, targetId, file);

        // 4. Cập nhật đường dẫn mới vào database (nếu sản phẩm đã tồn tại)
        if (product != null) {
            product.setThumbnail(fileName);
            productRepository.save(product);
        }

        return fileName;
    }

    /**
     * Upload nhiều ảnh cho variant sản phẩm.
     * Nếu có variantId, sẽ cập nhật vào variant trong DB.
     * Trả về danh sách đường dẫn tương đối để lưu vào imageList của variant.
     */
    public List<String> uploadVariantImages(Long productId, Long variantId, MultipartFile[] files) throws IOException {
        // 1. Kiểm tra variant tồn tại (nếu variantId được cung cấp)
        ProductVariant variant = null;
        if (variantId != null && variantId > 0) {
            variant = productVariantRepository.findById(variantId)
                    .orElseThrow(() -> new BusinessException("Không tìm thấy variant id: " + variantId));
            
            // Xóa các ảnh cũ nếu có
            if (variant.getImageList() != null && !variant.getImageList().isEmpty()) {
                variant.getImageList().forEach(imgPath -> FileUploadUtil.deleteOldFile(uploadDir, imgPath));
            }
        }

        // 2. Lưu các file mới
        long targetId = productId != null && productId > 0 ? productId : 0L;
        List<String> imagePaths = FileUploadUtil.saveMultipleFiles(uploadDir, EUploadType.PRODUCT, targetId, files);

        // 3. Cập nhật vào database (nếu variant đã tồn tại)
        if (variant != null) {
            variant.setImageList(imagePaths);
            productVariantRepository.save(variant);
        }

        return imagePaths;
    }


	
}


	
