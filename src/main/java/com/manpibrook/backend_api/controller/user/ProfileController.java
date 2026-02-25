package com.manpibrook.backend_api.controller.user;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.manpibrook.backend_api.dto.request.user.UpdateProfileRequest;
import com.manpibrook.backend_api.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user/profile")
public class ProfileController {
	@Autowired
	private AuthService authService;
	
	@PatchMapping("/update-info/{id}")
    public ResponseEntity<?> updateInfo(
            @PathVariable Long id, 
            @Valid @RequestBody UpdateProfileRequest request) {
        
        authService.updateProfileInfo(id, request);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Cập nhật thông tin thành công"
        ));
    }
	
	// Cập nhật ảnh đại diện
    @PostMapping("/update-avatar/{id}")
    public ResponseEntity<?> updateAvatar(
            @PathVariable Long id, 
            @RequestParam("file") MultipartFile file) throws IOException {
        
        String newImageUrl = authService.updateAvatar(id, file);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "imageUrl", newImageUrl,
            "message", "Cập nhật ảnh thành công"
        ));
    }
}