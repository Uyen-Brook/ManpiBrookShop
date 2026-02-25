package com.manpibrook.backend_api.service.admin;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manpibrook.backend_api.dto.request.admin.ShopInforRequest;
import com.manpibrook.backend_api.dto.response.ShopInforResponse;
import com.manpibrook.backend_api.entity.ShopInfor;
import com.manpibrook.backend_api.repository.ShopInforRepository;
import com.manpibrook.backend_api.utils.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopInforService {

    private final ShopInforRepository shopInforRepository;

    public List<ShopInforResponse> getAll() {
        return shopInforRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public ShopInforResponse getById(Long id) {
        ShopInfor info = shopInforRepository.findById(id)
                .orElseThrow(() -> new BusinessException("ShopInfor not found"));
        return mapToResponse(info);
    }

    public ShopInforResponse create(ShopInforRequest request) {
        ShopInfor info = new ShopInfor();
        applyRequest(request, info);
        return mapToResponse(shopInforRepository.save(info));
    }

    public ShopInforResponse update(Long id, ShopInforRequest request) {
        ShopInfor info = shopInforRepository.findById(id)
                .orElseThrow(() -> new BusinessException("ShopInfor not found"));
        applyRequest(request, info);
        return mapToResponse(shopInforRepository.save(info));
    }

    public void delete(Long id) {
        if (!shopInforRepository.existsById(id)) {
            throw new BusinessException("ShopInfor not found");
        }
        shopInforRepository.deleteById(id);
    }

    private void applyRequest(ShopInforRequest r, ShopInfor e) {
        if (r.getFromName() != null) e.setFromName(r.getFromName());
        if (r.getToPhone() != null) e.setToPhone(r.getToPhone());
        if (r.getToAddress() != null) e.setToAddress(r.getToAddress());
        if (r.getToWardCode() != null) e.setToWardCode(r.getToWardCode());
        if (r.getToProvinceCode() != null) e.setToProvinceCode(r.getToProvinceCode());
    }

    private ShopInforResponse mapToResponse(ShopInfor e) {
        ShopInforResponse res = new ShopInforResponse();
        res.setId(e.getId());
        res.setFromName(e.getFromName());
        res.setToPhone(e.getToPhone());
        res.setToAddress(e.getToAddress());
        res.setToWardCode(e.getToWardCode());
        res.setToProvinceCode(e.getToProvinceCode());
        return res;
    }
}