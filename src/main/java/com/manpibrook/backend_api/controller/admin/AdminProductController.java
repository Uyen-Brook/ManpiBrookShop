package com.manpibrook.backend_api.controller.admin;

import org.springframework.data.domain.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.manpibrook.backend_api.dto.request.admin.ProductRequest;
import com.manpibrook.backend_api.dto.request.admin.ProductVariantRequest;
import com.manpibrook.backend_api.dto.response.admin.ProductResponse;
import com.manpibrook.backend_api.dto.response.admin.VariantResponse;
import com.manpibrook.backend_api.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
    private final ProductService productService;
    // ================= GET ALL (Pagination) =================
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        return ResponseEntity.ok(
                productService.getAllProducts(page, size, sortBy, sortDir)
        );
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // ================= SEARCH =================
    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponse>> search(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        return ResponseEntity.ok(
                productService.getProductsByName(name, page, size, sortBy, sortDir)
        );
    }

    // ================= CREATE PRODUCT =================
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> createProduct(
            @RequestPart("data") ProductRequest request,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestPart(value = "variantImages", required = false) MultipartFile[] variantImages
    ) {

        ProductResponse response = productService.createProduct(
                request,
                thumbnail,
                variantImages
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ================= UPDATE PRODUCT =================
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return ResponseEntity.ok(
                productService.updateProduct(id, request)
        );
    }

    // ================= DELETE PRODUCT =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // ================= ADD VARIANT =================
    @PostMapping("/{productId}/variants")
    public ResponseEntity<ProductResponse> addVariant(
            @PathVariable Long productId,
            @Valid @RequestBody ProductVariantRequest variantRequest) {

        ProductResponse response =
                productService.addVariantToProduct(productId, variantRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ================= UPDATE VARIANT =================
    @PutMapping("/variants/{variantId}")
    public ResponseEntity<VariantResponse> updateVariant(
            @PathVariable Long variantId,
            @Valid @RequestBody ProductVariantRequest variantRequest) {

        return ResponseEntity.ok(
                productService.updateVariant(variantId, variantRequest)
        );
    }

    // ================= DELETE VARIANT =================
    @DeleteMapping("/variants/{variantId}")
    public ResponseEntity<Void> deleteVariant(@PathVariable Long variantId) {
        productService.deleteVariant(variantId);
        return ResponseEntity.noContent().build();
    }

    // ================= UPLOAD THUMBNAIL (NEW PRODUCT) =================
    @PostMapping("/upload/thumbnail")
    public ResponseEntity<?> uploadThumbnailForNew(
            @RequestParam("file") MultipartFile file) throws IOException {

        String imageUrl = productService.uploadThumbnail(0L, file);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "imageUrl", imageUrl,
                "message", "Upload thumbnail thành công"
        ));
    }

    // ================= UPLOAD THUMBNAIL (EXISTING PRODUCT) =================
    @PostMapping("/upload/thumbnail/{productId}")
    public ResponseEntity<?> uploadThumbnail(
            @PathVariable Long productId,
            @RequestParam("file") MultipartFile file) throws IOException {

        String imageUrl = productService.uploadThumbnail(productId, file);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "imageUrl", imageUrl,
                "message", "Cập nhật thumbnail thành công"
        ));
    }

    // ================= UPLOAD VARIANT IMAGES =================
    @PostMapping("/upload/variant-images")
    public ResponseEntity<?> uploadVariantImages(
            @RequestParam(defaultValue = "0") Long productId,
            @RequestParam(required = false) Long variantId,
            @RequestParam("files") MultipartFile[] files) throws IOException {

        List<String> imageUrls =
                productService.uploadVariantImages(productId, variantId, files);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "imageUrls", imageUrls,
                "message", "Upload ảnh variant thành công"
        ));
    }
}