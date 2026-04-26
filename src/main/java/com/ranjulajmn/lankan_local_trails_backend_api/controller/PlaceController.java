package com.ranjulajmn.lankan_local_trails_backend_api.controller;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlaceRequestDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlaceResponseDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PlaceResponseDTO> create(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam String openingTime,
            @RequestParam String closingTime,
            @RequestParam Float distance,
            @RequestParam(required = false) String travelTips,
            @RequestParam List<Long> categoryIds,
            @RequestParam MultipartFile image
    )
    {
        PlaceRequestDTO dto = new PlaceRequestDTO();
        dto.setName(name);
        dto.setDescription(description);
        dto.setLatitude(latitude);
        dto.setLongitude(longitude);
        dto.setOpeningTime(openingTime);
        dto.setClosingTime(closingTime);
        dto.setDistance(distance);
        dto.setTravelTips(travelTips);
        dto.setCategoryIds(categoryIds);

        return ResponseEntity.ok(placeService.create(dto, image));
    }

    @GetMapping
    public ResponseEntity<List<PlaceResponseDTO>> getAll(
            @RequestParam(required = false) Long categoryId
    ) {
        List<PlaceResponseDTO> places;
        if (categoryId != null && categoryId > 0) {
            System.out.println("Filtering by category: " + categoryId); // Debug log
            places = placeService.getPlacesByCategory(categoryId);
        } else {
            System.out.println("Getting all places"); // Debug log
            places = placeService.getAll();
        }
        return ResponseEntity.ok(places);
    }

    @GetMapping("/{id}")
    public PlaceResponseDTO getById(@PathVariable Long id) {
        return placeService.getById(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PlaceResponseDTO update(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam String openingTime,
            @RequestParam String closingTime,
            @RequestParam Float distance,
            @RequestParam(required = false) String travelTips,
            @RequestParam List<Long> categoryIds,
            @RequestParam(required = false) MultipartFile image
    ) {
        PlaceRequestDTO dto = new PlaceRequestDTO();
        dto.setName(name);
        dto.setDescription(description);
        dto.setLatitude(latitude);
        dto.setLongitude(longitude);
        dto.setOpeningTime(openingTime);
        dto.setClosingTime(closingTime);
        dto.setDistance(distance);
        dto.setTravelTips(travelTips);
        dto.setCategoryIds(categoryIds);

        return placeService.update(id, dto, image);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        placeService.delete(id, 1L);
        return ResponseEntity.ok("Place deleted successfully");
    }
}
