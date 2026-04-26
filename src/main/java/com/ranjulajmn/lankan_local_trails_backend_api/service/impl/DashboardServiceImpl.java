package com.ranjulajmn.lankan_local_trails_backend_api.service.impl;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.DashboardStatsDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.RecentPlaceDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.Place;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.PlaceCategory;
import com.ranjulajmn.lankan_local_trails_backend_api.repository.CategoryRepository;
import com.ranjulajmn.lankan_local_trails_backend_api.repository.PlaceCategoryRepository;
import com.ranjulajmn.lankan_local_trails_backend_api.repository.PlaceRepository;
import com.ranjulajmn.lankan_local_trails_backend_api.repository.UserRepository;
import com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final PlaceRepository placeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PlaceCategoryRepository placeCategoryRepository;

    @Override
    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        // ----- Basic counts (using existing repository methods) -----
        List<Place> activePlaces = placeRepository.findByDeletedAtIsNull();
        stats.setTotalPlaces(activePlaces.size());
        stats.setTotalCategories(categoryRepository.findByDeletedAtIsNull().size());
        stats.setTotalUsers(userRepository.findAll().size());
        stats.setTotalVisitPlans(0);   // future implementation

        // ----- Recent places (last 5 by creation date) -----
        List<RecentPlaceDTO> recentPlaces = activePlaces.stream()
                .sorted(Comparator.comparing(Place::getCreatedAt).reversed())
                .limit(5)
                .map(this::convertToRecentPlaceDTO)
                .collect(Collectors.toList());
        stats.setRecentPlaces(recentPlaces);

        // ----- New items in last 30 days -----
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minus(30, ChronoUnit.DAYS);
        long newUsers = userRepository.findAll().stream()
                .filter(u -> u.getCreatedAt() != null && u.getCreatedAt().isAfter(thirtyDaysAgo))
                .count();
        long newPlaces = activePlaces.stream()
                .filter(p -> p.getCreatedAt() != null && p.getCreatedAt().isAfter(thirtyDaysAgo))
                .count();
        stats.setNewUsersThisMonth(newUsers);
        stats.setNewPlacesThisMonth(newPlaces);

        // ----- User growth (percentage) -----
        long totalUsers = stats.getTotalUsers();
        if (totalUsers > 0 && newUsers > 0) {
            double previousUsers = totalUsers - newUsers;
            double growth = (previousUsers == 0) ? 100 : (newUsers / previousUsers) * 100;
            stats.setUserGrowth(Math.min(growth, 100));
        } else {
            stats.setUserGrowth(0);
        }

        // ----- Content completion (target: 10 places per category) -----
        long totalCategories = stats.getTotalCategories();
        if (totalCategories > 0) {
            long expectedTotal = totalCategories * 10; // 10 places per category
            double completion = ((double) stats.getTotalPlaces() / expectedTotal) * 100;
            stats.setContentCompletion(Math.min(completion, 100));
        } else {
            stats.setContentCompletion(0);
        }

        return stats;

    }

    private RecentPlaceDTO convertToRecentPlaceDTO(Place place) {
        RecentPlaceDTO dto = new RecentPlaceDTO();
        dto.setId(place.getId());
        dto.setName(place.getName());
        dto.setCreatedAt(place.getCreatedAt());

        // Fetch category names for this place
        List<PlaceCategory> linkList = placeCategoryRepository.findByPlaceId(place.getId());
        String categoryNames = linkList.stream()
                .map(link -> categoryRepository.findById(link.getCategoryId())
                        .map(cat -> cat.getName())
                        .orElse(""))
                .filter(name -> !name.isEmpty())
                .collect(Collectors.joining(", "));
        dto.setCategoryNames(categoryNames.isEmpty() ? "Uncategorized" : categoryNames);
        return dto;
    }
}
