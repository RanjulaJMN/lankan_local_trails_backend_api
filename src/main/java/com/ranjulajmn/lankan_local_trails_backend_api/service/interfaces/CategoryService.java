package com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.CategoryResponseDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.CategoryRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO create(CategoryResponseDTO dto);

    List<CategoryResponseDTO> getAll();

    CategoryResponseDTO getById(Long id);

    CategoryResponseDTO update(Long id, CategoryResponseDTO dto);

    void softDelete(Long id, Long userId);

    CategoryResponseDTO create(CategoryRequestDTO dto, MultipartFile image);

    CategoryResponseDTO update(Long id, CategoryRequestDTO dto, MultipartFile image);
}
