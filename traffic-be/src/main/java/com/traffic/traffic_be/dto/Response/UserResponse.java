package com.traffic.traffic_be.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String username;
    private String fullName;
    private String email;
}
