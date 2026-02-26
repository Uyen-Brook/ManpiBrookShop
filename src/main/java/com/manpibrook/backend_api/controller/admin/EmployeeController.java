package com.manpibrook.backend_api.controller.admin;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.manpibrook.backend_api.dto.request.admin.EmployeeRequest;
import com.manpibrook.backend_api.dto.response.admin.EmployeeResponse;
import com.manpibrook.backend_api.service.admin.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeResponse> create(@RequestBody EmployeeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable Long id,
                                                   @RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.delete(id));
    }

    @PostMapping("/avatar/{id}")
    public ResponseEntity<EmployeeResponse> uploadAvatar(@PathVariable Long id,
                                                         @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(employeeService.updateAvatar(id, file));
    }

    @PostMapping("/{id}/active")
    public ResponseEntity<EmployeeResponse> setActive(@PathVariable Long id,
                                                      @RequestParam("active") boolean active) {
        return ResponseEntity.ok(employeeService.toggleActive(id, active));
    }
}


