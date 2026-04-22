package com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO create(CategoryDTO dto);

    List<CategoryDTO> getAll();

    CategoryDTO getById(Long id);

    CategoryDTO update(Long id, CategoryDTO dto);

    void softDelete(Long id, Long userId);
}
