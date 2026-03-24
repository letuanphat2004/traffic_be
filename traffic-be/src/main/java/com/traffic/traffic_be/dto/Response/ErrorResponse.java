package com.traffic.traffic_be.dto.Response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter @Builder
public class ErrorResponse {
    private int status;          // Mã HTTP (400, 401, 500...)
    private String message;     // Thông điệp lỗi dễ hiểu cho người dùng
    private String details;     // Chi tiết lỗi (dùng để debug)
    private LocalDateTime timestamp;
}
