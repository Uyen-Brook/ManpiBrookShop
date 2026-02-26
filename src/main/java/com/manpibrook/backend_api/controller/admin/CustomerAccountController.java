package com.manpibrook.backend_api.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manpibrook.backend_api.dto.request.admin.CustomerAccountRequest;
import com.manpibrook.backend_api.service.admin.CustomerAccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/customers")
@RequiredArgsConstructor
public class CustomerAccountController {

    private final CustomerAccountService customerAccountService;

    @PatchMapping("/{id}/account")
    public ResponseEntity<String> updateAccount(@PathVariable Long id,
                                                @RequestBody CustomerAccountRequest request) {
        customerAccountService.updateCustomerAccount(id, request);
        return ResponseEntity.ok("Cập nhật tài khoản khách hàng thành công");
    }
}