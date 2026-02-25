package com.manpibrook.backend_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manpibrook.backend_api.dto.request.admin.SupplierRequest;
import com.manpibrook.backend_api.dto.response.SupplierResponse;
import com.manpibrook.backend_api.entity.Supplier;
import com.manpibrook.backend_api.repository.SupplierRepository;
import com.manpibrook.backend_api.utils.BusinessException;
@Service
public class SupplierService {
	@Autowired
	private SupplierRepository supplierRepository;

    // GET ALL
    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY ID
    public SupplierResponse getSupplierById(Integer id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Supplier not found with id: " + id));
        return mapToResponse(supplier);
    }

    // POST - CREATE
    public SupplierResponse createSupplier(SupplierRequest request) {
        Supplier supplier = new Supplier();
        mapRequestToEntity(request, supplier);
        if(supplierRepository.existsByTaxCode(request.getTaxCode())){
        	throw new BusinessException("Taxcode is exist");
        }
        else if(supplierRepository.existsBycode(request.getCode())) {
        	throw new BusinessException("Taxcode is exist");
        }
        // Lưu ý: Nếu DB không tự tăng ID, bạn phải set ID ở đây
        Supplier saved = supplierRepository.save(supplier);
        return mapToResponse(saved);
    }

    // PUT - UPDATE
    public SupplierResponse updateSupplier(Integer id, SupplierRequest request) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Supplier not found"));
        
        mapRequestToEntity(request, supplier);
        return mapToResponse(supplierRepository.save(supplier));
    }

    // DELETE
    public String deleteSupplier(Integer id) {
        if (!supplierRepository.existsById(id)) {
            throw new BusinessException("Supplier not found");
        }
        // Kiểm tra ràng buộc sản phẩm nếu cần trước khi xóa
        supplierRepository.deleteById(id);
        return "Xóa nhà cung cấp thành công";
    }

    // Helper: Chuyển từ Request sang Entity (Dùng cho cả Create và Update)
    private void mapRequestToEntity(SupplierRequest request, Supplier supplier) {
        supplier.setDisplayName(request.getDisplayName());
        supplier.setCode(request.getCode());
        supplier.setContactFullname(request.getContactFullname());
        supplier.setContactEmail(request.getContactEmail());
        supplier.setContactPhone(request.getContactPhone());
        supplier.setCompanyName(request.getCompanyName());
        supplier.setTaxCode(request.getTaxCode());
        supplier.setEmail(request.getEmail());
        supplier.setPhone(request.getPhone());
        supplier.setFax(request.getFax());
        supplier.setWebsite(request.getWebsite());
        supplier.setAdressUrl(request.getAdressUrl());
        supplier.setAddress(request.getAddress());
        supplier.setDescription(request.getDescription());
        supplier.setNote(request.getNote());
        supplier.setStatus(request.getStatus());
    }

    // Helper: Chuyển từ Entity sang Response
    private SupplierResponse mapToResponse(Supplier s) {
        return new SupplierResponse(
            s.getSupplierId(), s.getDisplayName(), s.getCode(), 
            s.getContactFullname(), s.getContactEmail(), s.getContactPhone(),
            s.getCompanyName(), s.getTaxCode(), s.getEmail(), s.getPhone(),
            s.getFax(), s.getWebsite(), s.getAdressUrl(), s.getAddress(),
            s.getDescription(), s.getNote(), s.getStatus()
        );
    }
}
