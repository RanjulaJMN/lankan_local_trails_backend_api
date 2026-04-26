package com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.CategoryRequestDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.CategoryResponseDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlaceRequestDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlaceResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlaceService {
    PlaceResponseDTO create(PlaceRequestDTO dto);

    List<PlaceResponseDTO> getAll();

    PlaceResponseDTO getById(Long id);

    //PlaceResponseDTO update(Long id, PlaceRequestDTO dto);

    void delete(Long id, Long userId);

    PlaceResponseDTO create(PlaceRequestDTO dto, MultipartFile image);

    PlaceResponseDTO update(Long id, PlaceRequestDTO dto, MultipartFile image);

    List<PlaceResponseDTO> getPlacesByCategory(Long categoryId);

}
