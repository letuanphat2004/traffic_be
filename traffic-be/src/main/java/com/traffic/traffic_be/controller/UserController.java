package com.traffic.traffic_be.controller;

import com.traffic.traffic_be.dto.Request.UpdateProfileRequest;
import com.traffic.traffic_be.dto.Response.UserResponse;
import com.traffic.traffic_be.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "Quản lý thông tin người dùng")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "Lấy thông tin cá nhân ")
    public ResponseEntity<UserResponse> getProfile() {
        return ResponseEntity.ok(userService.getMyProfile());
    }
    @PutMapping("/profile")
    @Operation(summary = "Cập nhật thông tin cá nhân (Cần Token)")
    public ResponseEntity<UserResponse> updateProfile(@RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateMyProfile(request));
    }

}