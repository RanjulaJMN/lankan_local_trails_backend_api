package com.ranjulajmn.lankan_local_trails_backend_api.service.impl;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlaceRequestDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlaceResponseDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.Place;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.PlaceCategory;
import com.ranjulajmn.lankan_local_trails_backend_api.mapper.PlaceMapper;
import com.ranjulajmn.lankan_local_trails_backend_api.repository.PlaceCategoryRepository;
import com.ranjulajmn.lankan_local_trails_backend_api.repository.PlaceRepository;
import com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceCategoryRepository placeCategoryRepository;
    private final PlaceMapper mapper;

    @Override
    public PlaceResponseDTO create(PlaceRequestDTO dto) {
        Place place = mapper.toEntity(dto);
        place.setCreatedAt(LocalDateTime.now());

        Place saved = placeRepository.save(place);

        // Save categories
        for (Long catId : dto.getCategoryIds()) {
            PlaceCategory pc = new PlaceCategory();
            pc.setPlaceId(saved.getId());
            pc.setCategoryId(catId);
            placeCategoryRepository.save(pc);
        }

        return mapper.toDTO(saved, dto.getCategoryIds());
    }

    @Override
    public List<PlaceResponseDTO> getAll() {
        return placeRepository.findByDeletedAtIsNull()
                .stream()
                .map(p -> {
                    List<Long> catIds = placeCategoryRepository.findByPlaceId(p.getId())
                            .stream()
                            .map(PlaceCategory::getCategoryId)
                            .toList();

                    return mapper.toDTO(p, catIds);
                }).toList();
    }

    @Override
    public PlaceResponseDTO getById(Long id) {
        Place place = placeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Place not found"));

        List<Long> catIds = placeCategoryRepository.findByPlaceId(id)
                .stream()
                .map(PlaceCategory::getCategoryId)
                .toList();

        return mapper.toDTO(place, catIds);
    }

    @Override
    public PlaceResponseDTO update(Long id, PlaceRequestDTO dto) {
        Place existing = placeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Place not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setLatitude(dto.getLatitude());
        existing.setLongitude(dto.getLongitude());
        existing.setOpeningTime(dto.getOpeningTime());
        existing.setClosingTime(dto.getClosingTime());
        existing.setDistance(dto.getDistance());
        existing.setTravelTips(dto.getTravelTips());
        existing.setImageUrl(dto.getImageUrl());
        existing.setUpdatedAt(LocalDateTime.now());

        Place saved = placeRepository.save(existing);

        // Update categories
        placeCategoryRepository.deleteByPlaceId(id);

        for (Long catId : dto.getCategoryIds()) {
            PlaceCategory pc = new PlaceCategory();
            pc.setPlaceId(id);
            pc.setCategoryId(catId);
            placeCategoryRepository.save(pc);
        }

        return mapper.toDTO(saved, dto.getCategoryIds());

    }

    @Override
    public void delete(Long id, Long userId) {
        Place place = placeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Place not found"));

        place.setDeletedAt(LocalDateTime.now());
        place.setDeletedBy(userId);

        placeRepository.save(place);
    }
}
