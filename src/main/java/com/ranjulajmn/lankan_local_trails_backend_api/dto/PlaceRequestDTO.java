package com.ranjulajmn.lankan_local_trails_backend_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PlaceRequestDTO {
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String openingTime;
    private String closingTime;
    private Float distance;
    private String travelTips;
    private String imageUrl;

    private List<Long> categoryIds;

}
