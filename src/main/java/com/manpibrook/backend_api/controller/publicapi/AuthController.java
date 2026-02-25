package com.manpibrook.backend_api.controller.publicapi;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manpibrook.backend_api.dto.request.user.RegisterRequest;
import com.manpibrook.backend_api.dto.request.user.UpdateProfileRequest;
import com.manpibrook.backend_api.service.AuthService;
import com.manpibrook.backend_api.service.EMailService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {
	private final EMailService emailService;
	@Autowired
	private final AuthService authService;
	// khởi tạo 
	public AuthController(EMailService emailService, AuthService authService) {
	        this.emailService = emailService;
	        this.authService = authService;
	    }
	
	// đăng kí
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
		authService.register(request);
		return ResponseEntity.ok("Đăng ký thành công");
	}

}
