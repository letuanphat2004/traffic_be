package com.traffic.traffic_be.dto.Request;

import lombok.Data;

@Data
public class LocationRequest {
    private String name;      // Tên đặt cho địa điểm (VD: Nhà bà ngoại)
    private String address;   // Địa chỉ chi tiết
    private Double latitude;
    private Double longitude;
}
