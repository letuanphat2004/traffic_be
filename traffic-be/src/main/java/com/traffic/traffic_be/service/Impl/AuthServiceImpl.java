package com.traffic.traffic_be.service.Impl;

import com.traffic.traffic_be.dto.Request.LoginRequest;
import com.traffic.traffic_be.dto.Request.RegisterRequest;
import com.traffic.traffic_be.entity.User;
import com.traffic.traffic_be.repository.UserRepository;
import com.traffic.traffic_be.service.AuthService;
import com.traffic.traffic_be.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Service Implementation
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public String register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username đã tồn tại!");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())) // Mã hóa BCrypt
                .fullName(request.getFullName())
                .email(request.getEmail())
                .build();

        userRepository.save(user);
        return "Đăng ký thành công!";
    }
    @Override
    public String login(LoginRequest request) {
        // 1. Kiểm tra user có tồn tại không
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Sai tài khoản hoặc mật khẩu!"));

        // 2. Kiểm tra mật khẩu (Sử dụng passwordEncoder để so khớp)
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Sai tài khoản hoặc mật khẩu!");
        }

        // 3. Tạo và trả về Token
        return jwtUtils.generateToken(user.getUsername());
    }
}
