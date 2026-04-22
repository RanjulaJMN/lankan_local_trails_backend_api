package com.ranjulajmn.lankan_local_trails_backend_api.service.impl;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.CategoryDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.Category;
import com.ranjulajmn.lankan_local_trails_backend_api.mapper.CategoryMapper;
import com.ranjulajmn.lankan_local_trails_backend_api.repository.CategoryRepository;
import com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repo;
    private final CategoryMapper mapper;

    public CategoryServiceImpl(CategoryRepository repo, CategoryMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public CategoryDTO create(CategoryDTO dto) {
        Category category = mapper.toEntity(dto);
        category.setCreatedAt(LocalDateTime.now());
        category.setCreatedBy(1L); // temporary
        return mapper.toDTO(repo.save(category));
    }

    @Override
    public List<CategoryDTO> getAll() {
        return repo.findByDeletedAtIsNull()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public CategoryDTO getById(Long id) {
        Category category = repo.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return mapper.toDTO(category);
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category category = repo.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setImgUrl(dto.getImgUrl());
        category.setUpdatedAt(LocalDateTime.now());

        return mapper.toDTO(repo.save(category));
    }

    @Override
    public void softDelete(Long id, Long userId) {
        Category category = repo.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setDeletedAt(LocalDateTime.now());
        category.setDeletedBy(userId);

        repo.save(category);
    }

}
