package com.ranjulajmn.lankan_local_trails_backend_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecentPlaceDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private String categoryNames;
}
