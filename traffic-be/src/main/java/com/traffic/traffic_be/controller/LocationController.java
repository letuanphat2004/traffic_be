package com.traffic.traffic_be.controller;

import com.traffic.traffic_be.dto.Request.LocationRequest;
import com.traffic.traffic_be.dto.Response.LocationResponse;
import com.traffic.traffic_be.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
@Tag(name = "Location", description = "Quản lý địa điểm yêu thích")
public class LocationController {
    private final LocationService locationService;

    @PostMapping("/favorite")
    @Operation(summary = "Lưu địa điểm vào danh sách yêu thích (Cần Token)")
    public ResponseEntity<String> addFavorite(@RequestBody LocationRequest request) {
        locationService.addFavoriteLocation(request);
        return ResponseEntity.ok("Đã thêm vào danh sách yêu thích!");
    }

    @GetMapping("/favorite")
    @Operation(summary = "Lấy danh sách địa điểm yêu thích của tôi (Cần Token)")
    public ResponseEntity<List<LocationResponse>> getFavorites() {
        return ResponseEntity.ok(locationService.getMyFavoriteLocations());
    }

    @PostMapping("/history")
    @Operation(summary = "Lưu vào lịch sử tìm kiếm (Cần Token)")
    public ResponseEntity<String> saveSearchHistory(@RequestBody LocationRequest request) {
        locationService.saveSearchHistory(request);
        return ResponseEntity.ok("Đã lưu vào lịch sử tìm kiếm!");
    }

    @GetMapping("/recent")
    @Operation(summary = "Lấy danh sách tìm kiếm gần đây (Cần Token)")
    public ResponseEntity<List<LocationResponse>> getRecent() {
        return ResponseEntity.ok(locationService.getRecentSearches());
    }
}
