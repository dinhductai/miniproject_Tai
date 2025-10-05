package com.microsv.user_service.controller;


import com.microsv.user_service.dto.request.UserCreationRequest;
import com.microsv.user_service.dto.request.UserUpdateRequest;
import com.microsv.user_service.dto.response.UserResponse;
import com.microsv.user_service.entity.User;
import com.microsv.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") // Đặt một tiền tố chung cho API
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    // Endpoint cho chức năng đăng ký (thường là public)
    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreationRequest request) {
        UserResponse response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Endpoint để lấy thông tin user theo ID (dành cho user đã đăng nhập hoặc admin)
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    // Endpoint để lấy danh sách tất cả user (chỉ dành cho ADMIN)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        // Lưu ý: Cần thêm logic getAllUsers() trong service
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Endpoint để cập nhật thông tin user (user tự cập nhật hoặc admin cập nhật)
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    // Endpoint để xóa user (chỉ dành cho ADMIN)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}