package com.traffic.traffic_be.service.Impl;

import com.traffic.traffic_be.dto.Request.LocationRequest;
import com.traffic.traffic_be.dto.Response.LocationResponse;
import com.traffic.traffic_be.entity.FavoriteLocation;
import com.traffic.traffic_be.entity.SearchHistory;
import com.traffic.traffic_be.entity.User;
import com.traffic.traffic_be.repository.FavoriteLocationRepository;
import com.traffic.traffic_be.repository.SearchHistoryRepository;
import com.traffic.traffic_be.repository.UserRepository;
import com.traffic.traffic_be.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final UserRepository userRepository;
    private final FavoriteLocationRepository locationRepository;
    private final SearchHistoryRepository searchHistoryRepository;

    public void addFavoriteLocation(LocationRequest request) {
        // 1. Lấy username của người đang đăng nhập từ Token
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        // 2. Kiểm tra trùng lặp (nếu cần)
        if (locationRepository.existsByLatitudeAndLongitudeAndUser(request.getLatitude(), request.getLongitude(), user)) {
            throw new RuntimeException("Địa điểm này đã có trong danh sách yêu thích!");
        }

        // 3. Lưu vào DB
        FavoriteLocation location = FavoriteLocation.builder()
                .name(request.getName())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .user(user)
                .build();

        locationRepository.save(location);
    }

    @Override
    public List<LocationResponse> getMyFavoriteLocations() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        return locationRepository.findByUser(user).stream()
                .map(loc -> LocationResponse.builder()
                        .id(loc.getId())
                        .name(loc.getName())
                        .address(loc.getAddress())
                        .latitude(loc.getLatitude())
                        .longitude(loc.getLongitude())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void saveSearchHistory(LocationRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        SearchHistory history = SearchHistory.builder()
                .query(request.getName()) // Giả sử tên địa điểm là nội dung tìm kiếm
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .user(user)
                .build();

        searchHistoryRepository.save(history);
    }

    @Override
    public List<LocationResponse> getRecentSearches() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        // Lấy 10 địa điểm gần nhất
        Pageable topTen = PageRequest.of(0, 10);
        return searchHistoryRepository.findByUserOrderByCreatedAtDesc(user, topTen).stream()
                .map(h -> LocationResponse.builder()
                        .id(h.getId())
                        .name(h.getQuery())
                        .address(h.getAddress())
                        .latitude(h.getLatitude())
                        .longitude(h.getLongitude())
                        .build())
                .collect(Collectors.toList());
    }

}
