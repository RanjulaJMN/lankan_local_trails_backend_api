package com.ranjulajmn.lankan_local_trails_backend_api.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class PlanResponseDTO {
    private Long id;
    private LocalDate planDate;
    private List<PlanItemDTO> items;

    @Data
    public static class PlanItemDTO {
        private Long placeId;
        private String placeName;
        private Integer order;
        private LocalTime arrivalTime;
        private LocalTime departureTime;
        private Integer travelTimeMinutes;
        private Float distanceKm;
        private Boolean alwaysOpen;
        private String openingTimeText;
        private String closingTimeText;
    }
}

