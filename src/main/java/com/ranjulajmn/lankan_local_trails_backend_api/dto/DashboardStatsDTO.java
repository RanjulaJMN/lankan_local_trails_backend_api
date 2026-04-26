package com.ranjulajmn.lankan_local_trails_backend_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class DashboardStatsDTO {
    private long totalPlaces;
    private long totalCategories;
    private long totalUsers;
    private long totalVisitPlans;      // placeholder for future
    private List<RecentPlaceDTO> recentPlaces;
    private double userGrowth;          // percentage
    private double contentCompletion;   // percentage
    private long newUsersThisMonth;
    private long newPlacesThisMonth;
}
