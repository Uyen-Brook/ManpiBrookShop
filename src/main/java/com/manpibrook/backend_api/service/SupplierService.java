package com.manpibrook.backend_api.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.manpibrook.backend_api.dto.request.admin.SupplierCreateRequest;
import com.manpibrook.backend_api.dto.request.admin.SupplierUpdateRequest;
import com.manpibrook.backend_api.dto.response.SupplierResponse;
import com.manpibrook.backend_api.entity.Supplier;
import com.manpibrook.backend_api.repository.SupplierRepository;
import com.manpibrook.backend_api.utils.BusinessException;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAllByOrderByDisplayName()
                .stream().map(this::mapToResponse).toList();
    }

    public SupplierResponse getSupplierById(Integer id) {
        Supplier supplier = supplierRepository.findBySupplierId(id)
                .orElseThrow(() -> new BusinessException("Supplier not found"));
        return mapToResponse(supplier);
    }

    public SupplierResponse createSupplier(SupplierCreateRequest request) {
        Supplier supplier = new Supplier();
        applyRequestToEntity(request, supplier);
        return mapToResponse(supplierRepository.save(supplier));
    }

    public SupplierResponse updateSupplier(Integer id, SupplierUpdateRequest request) {
        Supplier supplier = supplierRepository.findBySupplierId(id)
                .orElseThrow(() -> new BusinessException("Supplier not found"));
        applyRequestToEntity(request, supplier);
        return mapToResponse(supplierRepository.save(supplier));
    }

    public String deleteSupplier(Integer id) {
        if (!supplierRepository.existsBySupplierId(id)) {
            return "Không tìm thấy Supplier với ID: " + id;
        }
        // TODO: nếu cần kiểm tra còn Product dùng supplierId thì thêm check ở đây
        supplierRepository.deleteById(id);
        return "Đã xóa Supplier thành công";
    }
    
    private void applyRequestToEntity(SupplierCreateRequest r, Supplier s) {
        if (r.getDisplayName() != null) s.setDisplayName(r.getDisplayName());
        if (r.getCode() != null) s.setCode(r.getCode());
        if (r.getContactFullname() != null) s.setContactFullname(r.getContactFullname());
        if (r.getContactEmail() != null) s.setContactEmail(r.getContactEmail());
        if (r.getContactPhone() != null) s.setContactPhone(r.getContactPhone());
        if (r.getCompanyName() != null) s.setCompanyName(r.getCompanyName());
        if (r.getTaxCode() != null) s.setTaxCode(r.getTaxCode());
        if (r.getEmail() != null) s.setEmail(r.getEmail());
        if (r.getPhone() != null) s.setPhone(r.getPhone());
        if (r.getFax() != null) s.setFax(r.getFax());
        if (r.getWebsite() != null) s.setWebsite(r.getWebsite());
        if (r.getAdressUrl() != null) s.setAdressUrl(r.getAdressUrl());
        if (r.getAddress() != null) s.setAddress(r.getAddress());
        if (r.getDescription() != null) s.setDescription(r.getDescription());
        if (r.getNote() != null) s.setNote(r.getNote());
        if (r.getStatus() != null) s.setStatus(r.getStatus());
    }
    private void applyRequestToEntity(SupplierUpdateRequest r, Supplier s) {
        if (r.getDisplayName() != null) s.setDisplayName(r.getDisplayName());
        if (r.getCode() != null) s.setCode(r.getCode());
        if (r.getContactFullname() != null) s.setContactFullname(r.getContactFullname());
        if (r.getContactEmail() != null) s.setContactEmail(r.getContactEmail());
        if (r.getContactPhone() != null) s.setContactPhone(r.getContactPhone());
        if (r.getCompanyName() != null) s.setCompanyName(r.getCompanyName());
        if (r.getTaxCode() != null) s.setTaxCode(r.getTaxCode());
        if (r.getEmail() != null) s.setEmail(r.getEmail());
        if (r.getPhone() != null) s.setPhone(r.getPhone());
        if (r.getFax() != null) s.setFax(r.getFax());
        if (r.getWebsite() != null) s.setWebsite(r.getWebsite());
        if (r.getAdressUrl() != null) s.setAdressUrl(r.getAdressUrl());
        if (r.getAddress() != null) s.setAddress(r.getAddress());
        if (r.getDescription() != null) s.setDescription(r.getDescription());
        if (r.getNote() != null) s.setNote(r.getNote());
        if (r.getStatus() != null) s.setStatus(r.getStatus());
    }

    private SupplierResponse mapToResponse(Supplier s) {
        SupplierResponse res = new SupplierResponse();
        res.setSupplierId(s.getSupplierId());
        res.setDisplayName(s.getDisplayName());
        res.setCode(s.getCode());
        res.setContactFullname(s.getContactFullname());
        res.setContactEmail(s.getContactEmail());
        res.setContactPhone(s.getContactPhone());
        res.setCompanyName(s.getCompanyName());
        res.setTaxCode(s.getTaxCode());
        res.setEmail(s.getEmail());
        res.setPhone(s.getPhone());
        res.setFax(s.getFax());
        res.setWebsite(s.getWebsite());
        res.setAdressUrl(s.getAdressUrl());
        res.setAddress(s.getAddress());
        res.setDescription(s.getDescription());
        res.setNote(s.getNote());
        res.setStatus(s.getStatus());
        return res;
    }
}

//@Service
//public class SupplierService {
//	@Autowired
//	private SupplierRepository supplierRepository;
//
//    // GET ALL
//    public List<SupplierResponse> getAllSuppliers() {
//        return supplierRepository.findAll().stream()
//                .map(this::mapToResponse)
//                .toList();
//    }
//
//    // GET BY ID
//    public SupplierResponse getSupplierById(Integer id) {
//        Supplier supplier = supplierRepository.findById(id)
//                .orElseThrow(() -> new BusinessException("Supplier not found with id: " + id));
//        return mapToResponse(supplier);
//    }
//
//    // POST - CREATE
//    public SupplierResponse createSupplier(SupplierRequest request) {
//        Supplier supplier = new Supplier();
//        mapRequestToEntity(request, supplier);
//        if(supplierRepository.existsByTaxCode(request.getTaxCode())){
//        	throw new BusinessException("Taxcode is exist");
//        }
//        else if(supplierRepository.existsBycode(request.getCode())) {
//        	throw new BusinessException("Taxcode is exist");
//        }
//        // Lưu ý: Nếu DB không tự tăng ID, bạn phải set ID ở đây
//        Supplier saved = supplierRepository.save(supplier);
//        return mapToResponse(saved);
//    }
//
//    // PUT - UPDATE
//    public SupplierResponse updateSupplier(Integer id, SupplierRequest request) {
//        Supplier supplier = supplierRepository.findById(id)
//                .orElseThrow(() -> new BusinessException("Supplier not found"));
//        
//        mapRequestToEntity(request, supplier);
//        return mapToResponse(supplierRepository.save(supplier));
//    }
//
//    // DELETE
//    public String deleteSupplier(Integer id) {
//        if (!supplierRepository.existsById(id)) {
//            throw new BusinessException("Supplier not found");
//        }
//        // Kiểm tra ràng buộc sản phẩm nếu cần trước khi xóa
//        supplierRepository.deleteById(id);
//        return "Xóa nhà cung cấp thành công";
//    }
//
//    // Helper: Chuyển từ Request sang Entity (Dùng cho cả Create và Update)
//    private void mapRequestToEntity(SupplierRequest request, Supplier supplier) {
//        supplier.setDisplayName(request.getDisplayName());
//        supplier.setCode(request.getCode());
//        supplier.setContactFullname(request.getContactFullname());
//        supplier.setContactEmail(request.getContactEmail());
//        supplier.setContactPhone(request.getContactPhone());
//        supplier.setCompanyName(request.getCompanyName());
//        supplier.setTaxCode(request.getTaxCode());
//        supplier.setEmail(request.getEmail());
//        supplier.setPhone(request.getPhone());
//        supplier.setFax(request.getFax());
//        supplier.setWebsite(request.getWebsite());
//        supplier.setAdressUrl(request.getAdressUrl());
//        supplier.setAddress(request.getAddress());
//        supplier.setDescription(request.getDescription());
//        supplier.setNote(request.getNote());
//        supplier.setStatus(request.getStatus());
//    }
//
//    // Helper: Chuyển từ Entity sang Response
//    private SupplierResponse mapToResponse(Supplier s) {
//        return new SupplierResponse(
//            s.getSupplierId(), s.getDisplayName(), s.getCode(), 
//            s.getContactFullname(), s.getContactEmail(), s.getContactPhone(),
//            s.getCompanyName(), s.getTaxCode(), s.getEmail(), s.getPhone(),
//            s.getFax(), s.getWebsite(), s.getAdressUrl(), s.getAddress(),
//            s.getDescription(), s.getNote(), s.getStatus()
//        );
//    }
//}
