package com.traffic.traffic_be.controller;

import com.traffic.traffic_be.dto.Request.ForgotPasswordRequest;
import com.traffic.traffic_be.dto.Request.LoginRequest;
import com.traffic.traffic_be.dto.Request.RegisterRequest;
import com.traffic.traffic_be.dto.Request.ResetPasswordRequest;
import com.traffic.traffic_be.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Các API đăng ký và đăng nhập")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Đăng ký tài khoản mới")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }
    @PostMapping("/login")
    @Operation(summary = "Đăng nhập và nhận Token")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        String message = authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok(message);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        // Truyền cả email, otp và mật khẩu mới
        String message = authService.resetPassword(request.getEmail(), request.getOtp(), request.getNewPassword());
        return ResponseEntity.ok(message);
    }
}
