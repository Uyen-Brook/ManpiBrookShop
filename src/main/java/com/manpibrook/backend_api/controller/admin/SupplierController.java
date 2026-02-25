package com.manpibrook.backend_api.controller.admin;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manpibrook.backend_api.dto.request.admin.SupplierCreateRequest;
import com.manpibrook.backend_api.dto.request.admin.SupplierUpdateRequest;
import com.manpibrook.backend_api.dto.response.SupplierResponse;
import com.manpibrook.backend_api.service.SupplierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> getAll() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }
    

    @PostMapping("/create")
    public ResponseEntity<SupplierResponse> create(@RequestBody @Valid SupplierCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(supplierService.createSupplier(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SupplierResponse> update(@PathVariable Integer id,
                                                   @RequestBody SupplierUpdateRequest request) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(supplierService.deleteSupplier(id));
    }
}

