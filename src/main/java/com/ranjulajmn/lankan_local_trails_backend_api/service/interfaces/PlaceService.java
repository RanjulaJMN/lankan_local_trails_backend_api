package com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlaceRequestDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlaceResponseDTO;

import java.util.List;

public interface PlaceService {
    PlaceResponseDTO create(PlaceRequestDTO dto);

    List<PlaceResponseDTO> getAll();

    PlaceResponseDTO getById(Long id);

    PlaceResponseDTO update(Long id, PlaceRequestDTO dto);

    void delete(Long id, Long userId);
}
