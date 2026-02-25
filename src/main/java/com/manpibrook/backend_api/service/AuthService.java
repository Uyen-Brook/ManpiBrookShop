package com.manpibrook.backend_api.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.manpibrook.backend_api.dto.request.user.RegisterRequest;
import com.manpibrook.backend_api.dto.request.user.UpdateProfileRequest;
import com.manpibrook.backend_api.entity.Account;
import com.manpibrook.backend_api.entity.Customer;
import com.manpibrook.backend_api.entity.enums.ERole;
import com.manpibrook.backend_api.entity.enums.EUploadType;
import com.manpibrook.backend_api.repository.AccountRepository;
import com.manpibrook.backend_api.repository.CustomerRepository;
import com.manpibrook.backend_api.utils.BusinessException;
import com.manpibrook.backend_api.utils.FileUploadUtil;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
	@Autowired
    private final AccountRepository accountRepository;
	@Autowired
    private final CustomerRepository customerRepository;
//  private PasswordEncoder passwordEncoder;
	@Value("${upload.path}") // Lấy đường dẫn từ file config
	private String uploadDir;
	@Value("${spring.mail.username}")
	private String email;
	private final EMailService emailService;
    
	// NGHIỆP VỤ 1: CẬP NHẬT THÔNG TIN CHỮ
    public void updateProfileInfo(Long id, UpdateProfileRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy khách hàng"));
        Account account = customer.getAccount();

        if (request.getFullName() != null) customer.setFullName(request.getFullName());
        if (request.getPhone() != null) {
            // Kiểm tra trùng số điện thoại
            if (!request.getPhone().equals(customer.getPhone()) && 
                customerRepository.existsByPhone(request.getPhone())) {
                throw new BusinessException("Số điện thoại đã tồn tại");
            }
            customer.setPhone(request.getPhone());
        }
        
        if (request.getUserName() != null) {
            account.setUserName(request.getUserName());
        }
        // Lưu thông tin (Transactional tự động commit)
    }

    public String updateAvatar(Long id, MultipartFile file) throws IOException {
        // 1. Kiểm tra khách hàng tồn tại
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy khách hàng id: " + id));

        // 2. Xóa file vật lý cũ trên ổ đĩa (nếu có) để tránh rác server
        if (customer.getImageUrl() != null) {
            FileUploadUtil.deleteOldFile(uploadDir, customer.getImageUrl());
        }

        // 3. Lưu file mới với tên quy củ: PRFL_ID_TIMESTAMP.png
        // Sử dụng EUploadType.PROFILE để vào đúng thư mục 'profiles'
        String fileName = FileUploadUtil.saveFile(uploadDir, EUploadType.PROFILE, id, file);

        // 4. Cập nhật đường dẫn mới vào database
        customer.setImageUrl(fileName);
        customerRepository.save(customer);

        return fileName;
    }
    
// ĐĂNG KÍ CHƯA MÃ HÓA MẬT KHẨU
    public void register( RegisterRequest request) {

        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email đã được đăng kí, vui lòng điền email khác");
        }

        if (customerRepository.existsByPhone(request.getPhone())) {
            throw new BusinessException("Số điện thoại đã được đăng kí");
        }

        // 2. Tạo account
        Account account = new Account();
        account.setUserName(request.getUserName());
        account.setPassword(request.getPassword());
        account.setEmail(request.getEmail());
//      account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setRole(ERole.CUSTOMER);
        account.setActive(true);
        account.setStartDate(LocalDateTime.now());
        
        account = accountRepository.save(account);

        // 3. Tạo customer
        Customer customer = new Customer();
        customer.setFullName(request.getUserName());
        customer.setPhone(request.getPhone());
        customer.setAccount(account);
        
        customerRepository.save(customer);
        emailService.sendMail(email, account.getEmail(),"Chào mừng đến với Manpishop", "Hân hạnh");
        
        
    }
}

