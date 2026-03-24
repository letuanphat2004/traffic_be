package com.traffic.traffic_be.service.Impl;

import com.traffic.traffic_be.dto.Response.UserResponse;
import com.traffic.traffic_be.entity.User;
import com.traffic.traffic_be.repository.UserRepository;
import com.traffic.traffic_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Tự động tạo Constructor để Inject userRepository
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getMyProfile() {
        // 1. Lấy username từ Security Context
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Tìm User trong DB
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        // 3. Map sang DTO để trả về
        return UserResponse.builder()
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build();
    }
}