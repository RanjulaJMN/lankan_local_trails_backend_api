package com.ranjulajmn.lankan_local_trails_backend_api.mapper;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.CategoryResponseDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponseDTO toDTO(Category c){
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setDescription(c.getDescription());
        dto.setImgUrl(c.getImgUrl());
        return dto;
    }

    public Category toEntity(CategoryResponseDTO dto){
        Category c = new Category();
        c.setName(dto.getName());
        c.setDescription(dto.getDescription());
        c.setImgUrl(dto.getImgUrl());
        return c;
    }
}
