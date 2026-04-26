package com.ranjulajmn.lankan_local_trails_backend_api.service.impl;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.CategoryResponseDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.CategoryRequestDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.Category;
import com.ranjulajmn.lankan_local_trails_backend_api.mapper.CategoryMapper;
import com.ranjulajmn.lankan_local_trails_backend_api.repository.CategoryRepository;
import com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces.CategoryService;
import com.ranjulajmn.lankan_local_trails_backend_api.util.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repo;
    private final CategoryMapper mapper;
    private final FileStorageService fileStorageService;

    public CategoryServiceImpl(CategoryRepository repo, CategoryMapper mapper, FileStorageService fileStorageService) {
        this.repo = repo;
        this.mapper = mapper;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public CategoryResponseDTO create(CategoryResponseDTO dto) {
        Category category = mapper.toEntity(dto);
        category.setCreatedAt(LocalDateTime.now());
        category.setCreatedBy(1L); // temporary
        return mapper.toDTO(repo.save(category));
    }

    @Override
    public List<CategoryResponseDTO> getAll() {
        return repo.findByDeletedAtIsNull()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public CategoryResponseDTO getById(Long id) {
        Category category = repo.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return mapper.toDTO(category);
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryResponseDTO dto) {
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

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO dto, MultipartFile image) {
        String fileName = fileStorageService.saveFile(image);

        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setImgUrl("/images/" + fileName);

        category.setCreatedAt(LocalDateTime.now());
        category.setCreatedBy(1L);

        return mapper.toDTO(repo.save(category));
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto, MultipartFile image) {
        Category category = repo.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        if (image != null && !image.isEmpty()) {
            fileStorageService.deleteFile(category.getImgUrl());

            String fileName = fileStorageService.saveFile(image);
            category.setImgUrl("/images/" + fileName);
        }

        category.setUpdatedAt(LocalDateTime.now());

        return mapper.toDTO(repo.save(category));
    }

}
