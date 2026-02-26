package com.manpibrook.backend_api.service.admin;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.manpibrook.backend_api.dto.request.admin.EmployeeRequest;
import com.manpibrook.backend_api.dto.response.admin.EmployeeResponse;
import com.manpibrook.backend_api.entity.Account;
import com.manpibrook.backend_api.entity.Employee;
import com.manpibrook.backend_api.entity.enums.ERole;
import com.manpibrook.backend_api.entity.enums.EUploadType;
import com.manpibrook.backend_api.repository.AccountRepository;
import com.manpibrook.backend_api.repository.EmployeeRepository;
import com.manpibrook.backend_api.utils.BusinessException;
import com.manpibrook.backend_api.utils.FileUploadUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AccountRepository accountRepository;

    @Value("${upload.path}")
    private String uploadDir;

    public List<EmployeeResponse> getAll() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public EmployeeResponse getById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên id: " + id));
        return mapToResponse(employee);
    }

    public EmployeeResponse create(EmployeeRequest request) {
        validateRequest(request, null);

        Account account = new Account();
        account.setEmail(request.getEmail());
        account.setPassword(request.getPassword());
        account.setUserName(request.getUserName());
        account.setRole(request.getRole() != null ? request.getRole() : ERole.STAFF);
        account.setActive(request.getActive() == null || request.getActive());
        account.setStartDate(LocalDateTime.now());

        account = accountRepository.save(account);

        Employee employee = new Employee();
        employee.setFullName(request.getFullName());
        employee.setPhone(request.getPhone());
        employee.setPosition(request.getPosition());
        employee.setDepartment(request.getDepartment());
        employee.setDob(request.getDob());
        employee.setIdentityNumber(request.getIdentityNumber());
        employee.setSalary(request.getSalary());
        employee.setJoinDate(request.getJoinDate());
        employee.setAccount(account);

        employee = employeeRepository.save(employee);

        return mapToResponse(employee);
    }

    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên id: " + id));

        validateRequest(request, employee);

        employee.setFullName(request.getFullName());
        employee.setPhone(request.getPhone());
        employee.setPosition(request.getPosition());
        employee.setDepartment(request.getDepartment());
        employee.setDob(request.getDob());
        employee.setIdentityNumber(request.getIdentityNumber());
        employee.setSalary(request.getSalary());
        employee.setJoinDate(request.getJoinDate());

        Account account = employee.getAccount();
        if (account == null) {
            account = new Account();
        }

        account.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            account.setPassword(request.getPassword());
        }
        account.setUserName(request.getUserName());
        account.setRole(request.getRole() != null ? request.getRole() : ERole.STAFF);
        if (request.getActive() != null) {
            account.setActive(request.getActive());
        }

        if (account.getAccountId() == null) {
            account.setStartDate(LocalDateTime.now());
        }

        account = accountRepository.save(account);
        employee.setAccount(account);

        employee = employeeRepository.save(employee);

        return mapToResponse(employee);
    }

    public String delete(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên id: " + id));
        employeeRepository.delete(employee);
        return "Đã xoá nhân viên thành công";
    }

    public EmployeeResponse updateAvatar(Long id, MultipartFile file) throws IOException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên id: " + id));

        if (employee.getImageUrl() != null) {
            FileUploadUtil.deleteOldFile(uploadDir, employee.getImageUrl());
        }

        String fileName = FileUploadUtil.saveFile(uploadDir, EUploadType.PROFILE, id, file);

        employee.setImageUrl(fileName);
        employee = employeeRepository.save(employee);

        return mapToResponse(employee);
    }

    public EmployeeResponse toggleActive(Long id, boolean active) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên id: " + id));
        Account account = employee.getAccount();
        if (account == null) {
            throw new BusinessException("Nhân viên chưa được cấp tài khoản");
        }
        account.setActive(active);
        accountRepository.save(account);
        return mapToResponse(employee);
    }

    private void validateRequest(EmployeeRequest request, Employee current) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new BusinessException("Email không được để trống");
        }
        if (request.getUserName() == null || request.getUserName().isBlank()) {
            throw new BusinessException("UserName không được để trống");
        }
        if (current == null && (request.getPassword() == null || request.getPassword().isBlank())) {
            throw new BusinessException("Mật khẩu không được để trống khi tạo mới");
        }
        if (request.getFullName() == null || request.getFullName().isBlank()) {
            throw new BusinessException("Họ tên không được để trống");
        }
        if (request.getPhone() == null || request.getPhone().isBlank()) {
            throw new BusinessException("Số điện thoại không được để trống");
        }

        if (current == null || !request.getPhone().equals(current.getPhone())) {
            if (employeeRepository.existsByPhone(request.getPhone())) {
                throw new BusinessException("Số điện thoại đã tồn tại");
            }
        }
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        Account account = employee.getAccount();

        return EmployeeResponse.builder()
                .id(employee.getId())
                .fullName(employee.getFullName())
                .phone(employee.getPhone())
                .imageUrl(employee.getImageUrl())
                .position(employee.getPosition())
                .department(employee.getDepartment())
                .dob(employee.getDob())
                .identityNumber(employee.getIdentityNumber())
                .salary(employee.getSalary())
                .joinDate(employee.getJoinDate())
                .accountId(account != null ? account.getAccountId() : null)
                .email(account != null ? account.getEmail() : null)
                .userName(account != null ? account.getUserName() : null)
                .role(account != null ? account.getRole() : null)
                .active(account != null && account.isActive())
                .build();
    }
}