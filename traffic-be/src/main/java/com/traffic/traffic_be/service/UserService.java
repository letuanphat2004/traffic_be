package com.traffic.traffic_be.service;
import com.traffic.traffic_be.dto.Response.UserResponse;

public interface UserService {
    UserResponse getMyProfile();
}