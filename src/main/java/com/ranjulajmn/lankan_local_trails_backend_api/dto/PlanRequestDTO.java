package com.ranjulajmn.lankan_local_trails_backend_api.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PlanRequestDTO {
    private LocalDate planDate;
    private List<Long> placeIds;
}
