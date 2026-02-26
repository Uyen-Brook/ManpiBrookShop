package com.manpibrook.backend_api.service.admin;

import org.springframework.stereotype.Service;

import com.manpibrook.backend_api.dto.request.admin.CustomerAccountRequest;
import com.manpibrook.backend_api.entity.Account;
import com.manpibrook.backend_api.entity.Customer;
import com.manpibrook.backend_api.repository.CustomerRepository;
import com.manpibrook.backend_api.utils.BusinessException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerAccountService {

    private final CustomerRepository customerRepository;

    public void updateCustomerAccount(Long customerId, CustomerAccountRequest request) {
        Customer customer = customerRepository.findByAccount_AccountId(customerId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy khách hàng id: " + customerId));

        Account account = customer.getAccount();
        if (account == null) {
            throw new BusinessException("Khách hàng chưa có tài khoản");
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            account.setPassword(request.getPassword());
        }
        if (request.getActive() != null) {
            account.setActive(request.getActive());
        }
    }
}
