package com.traffic.traffic_be.service;

import com.traffic.traffic_be.dto.Request.LoginRequest;
import com.traffic.traffic_be.dto.Request.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);
    String login(LoginRequest request);
    String forgotPassword(String email);
    String resetPassword(String email, String otp, String newPassword);
}

