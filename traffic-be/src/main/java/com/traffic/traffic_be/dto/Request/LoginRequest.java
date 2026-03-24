package com.traffic.traffic_be.dto.Request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
