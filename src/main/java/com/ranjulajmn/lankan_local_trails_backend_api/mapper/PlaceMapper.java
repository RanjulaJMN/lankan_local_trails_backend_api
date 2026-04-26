package com.ranjulajmn.lankan_local_trails_backend_api.mapper;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlaceRequestDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlaceResponseDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.Place;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaceMapper {

    public Place toEntity(PlaceRequestDTO dto) {
        Place place = new Place();
        place.setName(dto.getName());
        place.setDescription(dto.getDescription());
        place.setLatitude(dto.getLatitude());
        place.setLongitude(dto.getLongitude());
        place.setOpeningTime(dto.getOpeningTime());
        place.setClosingTime(dto.getClosingTime());
        place.setDistance(dto.getDistance());
        place.setTravelTips(dto.getTravelTips());
        return place;
    }

    public PlaceResponseDTO toDTO(Place place, List<Long> categoryIds) {
        PlaceResponseDTO dto = new PlaceResponseDTO();
        dto.setId(place.getId());
        dto.setName(place.getName());
        dto.setDescription(place.getDescription());
        dto.setLatitude(place.getLatitude());
        dto.setLongitude(place.getLongitude());
        dto.setOpeningTime(place.getOpeningTime());
        dto.setClosingTime(place.getClosingTime());
        dto.setDistance(place.getDistance());
        dto.setTravelTips(place.getTravelTips());
        dto.setImageUrl(place.getImageUrl());
        dto.setCategoryIds(categoryIds);
        return dto;
    }
}
